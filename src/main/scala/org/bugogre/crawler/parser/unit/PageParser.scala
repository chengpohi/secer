package org.bugogre.crawler.parser.unit

import akka.actor.{Actor, Props}
import org.bugogre.crawler.fetcher.WebFetcher
import org.bugogre.crawler.html.Page
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.PageIndexer
import org.bugogre.crawler.url.Url
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.collection.JavaConverters._

/**
 * Created by xiachen on 12/16/14.
 */
class PageParser extends Actor{
  //val log = Logging(context.system, this)
  val pageIndexer = context.actorOf(Props[PageIndexer], "PageIndexer")
  val webFetcher = context.actorOf(Props[WebFetcher], "WebFetcher")

  def parse(html: String ): Page = parse(html, null)

  def fetcher(doc: Document): Unit = {
    doc.select("a").asScala.map(u => println(u.attr("href")))
  }

  def parse(html: String, url: Url): Page = {
    val doc = Jsoup.parse(html)
    //fetcher(doc)
    Page(doc.title, doc, url, Some("www"), Some("china"))
  }

  def parse(web: Web[Url]): Page = parse(web.html, web.url)

  def receive = {
    case str: String => {
    }
    case web: Web[Url] => {
      val page = parse(web)
      pageIndexer ! page
    }
  }
}
