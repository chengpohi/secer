package org.bugogre.crawler.parser
import java.util.concurrent.{Executors, LinkedBlockingQueue}

import org.bugogre.crawler.config.SecConfig
import org.bugogre.crawler.html.Html
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.url.Url
import org.jsoup.Jsoup

import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent._

/**
 * Created by xiachen on 12/16/14.
 */
class HtmlParser extends Runnable{
  val webs = new LinkedBlockingQueue[Web[Url]]()
  def parse(html: String): Html = {
    val doc = Jsoup.parse(html)
    Html(doc.title, doc, null)
  }

  def parse(html: String, url: Url): Html = {
    val doc = Jsoup.parse(html)
    Html(doc.title, doc, url)
  }

  def run(): Unit = {
    while(true) {
      val web = webs take
      val html: Future[Html] = future {
        parse(web.html)
      }
    }
  }

  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.threads.getInt("fetcher")))
}
