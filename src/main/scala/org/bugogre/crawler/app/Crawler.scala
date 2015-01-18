package org.bugogre.crawler.app

import java.util.concurrent.TimeUnit

import org.bugogre.crawler.fetcher._
import org.bugogre.crawler.parser.unit.PageParser
import org.bugogre.crawler.url.Url

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem

import org.slf4j.LoggerFactory

/**
 * author: chengpohi@gmail.com
 */
object Crawler{
  def main(args: Array[String]) {
    val system = ActorSystem("Crawler")
    system.actorOf(Props[Crawler], "crawler")
  }
}

class Crawler extends Actor {

  val LOG = LoggerFactory.getLogger(getClass.getName)

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
