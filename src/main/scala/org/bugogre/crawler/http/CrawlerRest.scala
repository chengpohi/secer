package org.bugogre.crawler.http

import akka.actor.{Actor, Props}
import org.bugogre.crawler.app.Crawler
import org.siny.web.rest.controller.RestAction

/**
 * seccrawler
 * Created by chengpohi on 8/22/15.
 */
class CrawlerRest extends RestAction with Actor {
  val crawler = context.actorOf(Props[Crawler], "Crawler")
  override def receive: Receive = ???
}
