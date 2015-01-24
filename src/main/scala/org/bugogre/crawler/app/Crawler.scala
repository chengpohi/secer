package org.bugogre.crawler.app

import akka.actor.{Actor, ActorSystem, Props}
import org.bugogre.crawler.fetcher._

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

  override def preStart(): Unit = {
    //webFetcher ! Url("http://www.baidu.com")
  }

  def receive = {
    case str: String => {
      println(str)
    }
  }
}
