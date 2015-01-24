package org.bugogre.crawler.app

import akka.actor.{Actor, ActorSystem, Props}
import org.bugogre.crawler.fetcher._
import org.bugogre.crawler.url.FetchItem
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
  val webFetcher = context.actorOf(Props[WebFetcher], "webFetcher")
  val LOG = LoggerFactory.getLogger(getClass.getName)

  override def preStart(): Unit = {
    webFetcher ! FetchItem("http://www.baidu.com")
  }

  def receive = {
    case str: String => {
      println(str)
    }
  }
}
