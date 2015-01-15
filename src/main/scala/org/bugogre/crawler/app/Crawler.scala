package org.bugogre.crawler.app

import java.util.concurrent.TimeUnit

import org.bugogre.crawler.fetcher._
import org.bugogre.crawler.url.Url
import org.bugogre.crawler.parser.HtmlParser

import akka.actor.Actor
import akka.actor.Props

import org.slf4j.LoggerFactory

/**
 * author: chengpohi@gmail.com
 */
object Crawler{
  def main(args: Array[String]) {
    akka.Main.main(Array(classOf[Crawler].getName))
  }
}

class Crawler extends Actor {

  val LOG = LoggerFactory.getLogger(getClass.getName);

  val webFetcher = context.actorOf(Props[WebFetcher], "webFetcher")

  override def preStart(): Unit = {
    webFetcher ! Url("http://www.baidu.com")
  }

  def receive = {
    case str: String => {
      println(str)
    }
  }
}
