package org.bugogre.crawler.parser
import java.util.concurrent.{Executors, LinkedBlockingQueue}

import org.bugogre.crawler.config.SecConfig
import org.bugogre.crawler.html.Html
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.url.Url
import org.jsoup.Jsoup

import org.slf4j.LoggerFactory

import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent._

import akka.actor.Actor
import akka.actor.Props

/**
 * Created by xiachen on 12/16/14.
 */
class HtmlParser extends Actor{
  val LOG = LoggerFactory.getLogger(getClass.getName)

  def parse(html: String): Html = {
    val doc = Jsoup.parse(html)
    Html(doc.title, doc, null)
  }

  def parse(html: String, url: Url): Html = {
    val doc = Jsoup.parse(html)
    Html(doc.title, doc, url)
  }

  def receive = {
    case str: String => {
      println("I am HtmlParser")
      sender ! str
    }
    case web: Web[Url] => {
      println("HtmlParser: " + web.html.length)
    }
  }
}
