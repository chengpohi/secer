/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi

import akka.actor.{Actor, ActorSystem, Props}
import com.github.chengpohi.config.ClientConfig
import com.github.chengpohi.model.FetchItem
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory


object TransportClient {
  val actorSystem = ActorSystem("Crawler", ConfigFactory.load("crawler"))
  val crawler = actorSystem.actorOf(Props(new TransportClient))
}

class TransportClient extends Actor {
  val crawler =
    context.actorSelection(s"akka.tcp://Crawler@${ClientConfig.CRAWLER_HOST}/user/crawler")

  lazy val LOGGER = LoggerFactory.getLogger(getClass.getName)

  def receive: Receive = {
    case fetchItem: FetchItem => {
      crawler ! fetchItem
    }
    case str: String => {
      LOGGER.info(str)
      crawler ! str
    }
  }
}
