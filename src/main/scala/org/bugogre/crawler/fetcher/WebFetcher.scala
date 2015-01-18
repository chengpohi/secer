package org.bugogre.crawler.fetcher

import org.bugogre.crawler.httpclient._
import org.bugogre.crawler.config._

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

import org.bugogre.crawler.parser.unit.HtmlParser
import org.bugogre.crawler.rule.Rule
import org.bugogre.crawler.url.Url

import scala.util.{Success, Failure}
import scala.concurrent._

import org.slf4j.LoggerFactory

import akka.actor.Actor
import akka.actor.Props

class WebFetcher extends Actor {

  lazy val rule = Rule(SecConfig.excludeUrlPatterns)

  val LOG = LoggerFactory.getLogger(getClass.getName)

  val htmlParser = context.actorOf(Props[HtmlParser], "htmlParser")

  override def preStart(): Unit = {
  }

  def parse(web: Web[Url]): Unit = {
    htmlParser ! web
  }

  def fetch(url: Url): Web[Url] = {
    WebFactory ==> url
  }

  def receive = {
    case str: String => {
      println("I am WebFetcher.")
      htmlParser ! str
    }
    case url: Url => {
      url.filterByRule(rule) match {
        case false => parse(fetch(url))
        case _ =>
      }
    }
  }
}
