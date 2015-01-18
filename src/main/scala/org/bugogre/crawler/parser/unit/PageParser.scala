package org.bugogre.crawler.parser.unit

import akka.actor.Actor
import org.bugogre.crawler.html.Page
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.url.Url
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

/**
 * Created by xiachen on 12/16/14.
 */
class PageParser extends Actor{
  val LOG = LoggerFactory.getLogger(getClass.getName)

  def parse(html: String ): Page = parse(html, null)

  def parse(html: String, url: Url): Page = {
    val doc = Jsoup.parse(html)
    Page(doc.title, doc, url)
  }

  def parse(web: Web[Url]): Page = parse(web.html, web.url)

  def receive = {
    case str: String => {
      println("I am HtmlParser")
      sender ! str
    }
    case web: Web[Url] => sender() ! parse(web)
  }
}
