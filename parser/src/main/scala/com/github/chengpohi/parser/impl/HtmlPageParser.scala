package com.github.chengpohi.parser.impl

import java.net.URL
import java.util.concurrent.Executors

import akka.actor.ActorRef
import com.github.chengpohi.model._
import com.github.chengpohi.util.SecHelper
import SecHelper._
import com.github.chengpohi.parser.config.ParserConfig
import com.github.chengpohi.parser.html.HtmlToMarkdown
import com.github.chengpohi.parser.url.UrlNormalizer
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scala.concurrent._

/**
 * Created by xiachen on 3/1/15.
 */
class HtmlPageParser(pageIndexer: ActorRef) {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  lazy val m = java.security.MessageDigest.getInstance("MD5")
  lazy val htmlToMarkdown = new HtmlToMarkdown

  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(ParserConfig.MAX_THREADS))

  def normalize(url: String): URL = UrlNormalizer.normalize(url)

  def hash(s: String): String = {
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }

  def parse(html: String): (Page, List[FetchItem]) = parse(Jsoup.parse(html), null)

  def hrefs(doc: Document, item: FetchItem): List[FetchItem] = {
    for {
      i: FetchItem <- doc.select("a").asScala.toList
        .map(e => e.absUrl("href"))
        .filter(e => e.length != 0)
        .map(e => FetchItem(normalize(e), item.indexName, item.indexType, item.selectors, item.urlRegex))
    } yield i
  }


  def parse(doc: Document, item: FetchItem): (Page, List[FetchItem]) =
    (Page(doc, item, hashString(doc.html), hash(item.url.toString), parseBySelector(doc, item.selectors)), hrefs(doc, item))

  def selectBySelector(doc: Document, selector: String): String = {
    doc.select(selector).first() match {
      case el: Element => el.html()
      case null => ""
    }
  }

  def parseBySelector(doc: Document, fieldSelectors: List[FieldSelector]): List[IndexField] = {
    for (fieldSelector <- fieldSelectors) yield IndexField(fieldSelector.field,
      htmlToMarkdown.parse(selectBySelector(doc, fieldSelector.selector)))
  }

  def parse(web: Web): (Page, List[FetchItem]) = parse(web.doc, web.fetchItem)


  def asyncParse(sender: ActorRef, web: Web): Future[String] = {
    Future {
      blocking {
        val res = parse(web)
        pageIndexer ! res._1

        LOG.info(s"Parser Seeds size: ${res._2.length}")

        sender ! res._2
        ""
      }
    }
  }
}
