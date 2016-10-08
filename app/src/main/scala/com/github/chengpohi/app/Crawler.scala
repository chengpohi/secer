package com.github.chengpohi.app

import akka.actor._
import com.github.chengpohi.PageFetcherService
import com.github.chengpohi.app.http.HttpRunner
import com.github.chengpohi.app.http.actions.RestActions.registerHandler
import com.github.chengpohi.model.FetchItem
import com.typesafe.config.ConfigFactory
import org.jboss.netty.handler.codec.http.HttpMethod._
import org.slf4j.LoggerFactory

import scala.language.postfixOps

/**
  * author: chengpohi@gmail.com
  */
object Crawler {
  def main(args: Array[String]) {
    val system = ActorSystem("Crawler", ConfigFactory.load("crawler"))
    system.actorOf(Props[Crawler], "crawler")
  }
}

class Crawler extends Actor with HttpRunner {
  override lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  override lazy val config = ConfigFactory.load("http")
  lazy val fetcher = context.actorOf(Props[PageFetcherService], "fetcher")

  override def preStart(): Unit = this.start()

  def receive = {
    case fetchItem: FetchItem =>
      fetcher ! fetchItem
    case Terminated(_) =>
      context.unbecome()
    case str: String =>
      LOG.info(str)
  }

  def seed(fetchItem: FetchItem): String = {
    self ! fetchItem
    s"""{"indexName": ${fetchItem.indexName}}"""
  }

  override def registerPath() = {
    registerHandler(POST, "/seed", seed)
  }
}
