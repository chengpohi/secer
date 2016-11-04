package com.github.chengpohi.parser.impl

import java.util.concurrent.Executors

import akka.actor.ActorRef
import com.github.chengpohi.HDSLInterpreter
import com.github.chengpohi.model._
import com.github.chengpohi.parser.config.ParserConfig
import com.github.chengpohi.parser.url.UrlNormalizer
import com.github.chengpohi.util.Utils._
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

  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(ParserConfig.PARSER_POOL))

  def normalize(url: String): String = UrlNormalizer.normalize(url)

  val SIG_NUM: Int = 1
  val HEX: Int = 16

  def hash(s: String): String = {
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(SIG_NUM, m.digest()).toString(HEX)
  }

  def hrefs(doc: Document, item: FetchItem): List[FetchItem] = {
    for {
      i: FetchItem <- doc.select("a").asScala.toList
        .map(e => e.absUrl("href"))
        .filter(e => e.length != 0)
        .map(e => FetchItem(normalize(e), item.indexName, item.indexType, item.selectors, item.urlRegex))
    } yield i
  }


  def parse(doc: Document, item: FetchItem): (IndexItem, List[FetchItem]) =
    (IndexItem(doc, item, hashString(doc.html), hash(item.url), parseBySelector(doc, item.selectors)), hrefs(doc, item))

  def selectBySelector(doc: Document, selector: String): String = {
    doc.select(selector).first() match {
      case el: Element => el.html()
      case _ => ""
    }
  }

  def parseBySelector(doc: Document, fieldSelectors: String): Map[String, Any] = {
    val interpreter: HDSLInterpreter = new HDSLInterpreter(doc)
    interpreter.intercept(fieldSelectors)
  }

  def parse(web: (FetchItem, Document)): (IndexItem, List[FetchItem]) = parse(web._2, web._1)


  def asyncParse(sender: ActorRef, web: (FetchItem, Document)): Future[String] = {
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
