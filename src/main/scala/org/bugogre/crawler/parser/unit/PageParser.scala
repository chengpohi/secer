package org.bugogre.crawler.parser.unit

import akka.actor.{Actor, Props}
import org.bugogre.crawler.fetcher.PageFetcher
import org.bugogre.crawler.html.Page
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.PageIndexer
import org.bugogre.crawler.url.FetchItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

/**
 * Created by xiachen on 12/16/14.
 */
class PageParser extends Actor{
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  val pageIndexer = context.actorOf(Props[PageIndexer], "PageIndexer")
  val webFetcher = context.actorOf(Props[PageFetcher], "WebFetcher")

  def parse(html: String ): Page = parse(html, null)

  def fetcher(doc: Document): Unit = {
    doc.select("a").asScala.map(u => println(u.attr("href")))
  }

  def parse(html: String, url: FetchItem): Page = {
    val doc = Jsoup.parse(html)
    //fetcher(doc)
    Page(doc.title, doc, url, Some("www"), Some("china"))
  }

  def parse(web: Web[FetchItem]): Page = parse(web.html, web.url)

  def receive = {
    case str: String => {
    }
    case web: Web[FetchItem] => {
      LOG.info("Parse Url: " + web.url.url)
      val page = parse(web)
      pageIndexer ! page
    }
  }
}
