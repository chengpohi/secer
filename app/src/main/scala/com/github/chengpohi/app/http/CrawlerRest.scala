package com.github.chengpohi.app.http

import akka.actor.{Actor, Props}

/**
 * seccrawler
 * Created by chengpohi on 8/22/15.
 */
class CrawlerRest extends RestAction with Actor {
  val crawler = context.actorOf(Props[Crawler], "Crawler")
  override def receive: Receive = ???
}
