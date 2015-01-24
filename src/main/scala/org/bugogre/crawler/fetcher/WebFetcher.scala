package org.bugogre.crawler.fetcher

import akka.actor.{Actor, Props}
import org.bugogre.crawler.config._
import org.bugogre.crawler.httpclient._
import org.bugogre.crawler.parser.unit.PageParser
import org.bugogre.crawler.rule.Rule
import org.bugogre.crawler.url.Url

class WebFetcher extends Actor {

  lazy val rule = Rule(SecConfig.excludeUrlPatterns)

  //val log = Logging(context.system, this)

  val pageParser = context.actorOf(Props[PageParser], "htmlParser")

  override def preStart(): Unit = {
  }

  def fetch(url: Url): Web[Url] = {
    WebFactory ==> url
  }

  def receive = {
    case str: String => {
      pageParser ! str
    }
    case url: Url => {
      url.filterByRule(rule) match {
        case false => {
          pageParser ! fetch(url)
        }
        case _ => {
        }
      }
    }
  }
}
