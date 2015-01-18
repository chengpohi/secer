package org.bugogre.crawler.parser.unit

import akka.actor.Actor
import org.bugogre.crawler.html.Html
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.url.Url
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

/**
 * Created by xiachen on 12/16/14.
 */
class HtmlParser extends Actor{
  val LOG = LoggerFactory.getLogger(getClass.getName)

  def parse(html: String ): Html = parse(html, null)

  def parse(html: String, url: Url): Html = {
    val doc = Jsoup.parse(html)
    Html(doc.title, doc, url)
  }

  def parse(web: Web[Url]): Html = parse(web.html, web.url)

  def receive = {
    case str: String => {
      println("I am HtmlParser")
      sender ! str
    }
    case web: Web[Url] => parse(web)
  }
}
