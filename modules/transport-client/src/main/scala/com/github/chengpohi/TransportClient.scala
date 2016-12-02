/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.github.chengpohi.config.ClientConfig
import com.github.chengpohi.model.FetchItem
import com.typesafe.config.ConfigFactory


object TransportClient {
  val actorSystem = ActorSystem("Crawler", ConfigFactory.load("crawler"))
  val crawler = actorSystem.actorOf(Props(new TransportClient))
}

class TransportClient extends Actor with ActorLogging {
  val crawler =
    context.actorSelection(s"akka.tcp://Crawler@${ClientConfig.CRAWLER_HOST}/user/crawler")

  def receive: Receive = {
    case fetchItem: FetchItem => {
      crawler ! fetchItem
    }
    case str: String => {
      log.info(str)
      crawler ! str
    }
  }
}
