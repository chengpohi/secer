package com.github.chengpohi.app

import akka.actor._
import com.github.chengpohi.{ELKInterpreter, PageFetcherService}
import com.github.chengpohi.app.http.HttpRunner
import com.github.chengpohi.app.http.actions.RestActions.registerHandler
import com.github.chengpohi.model.{DSL, FetchItem}
import com.github.chengpohi.registry.ELKCommandRegistry
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
  lazy val replInterpreter = new ELKInterpreter(ELKCommandRegistry)

  override def preStart(): Unit = this.start()

  def receive: Receive = {
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

  def repl(dsl: DSL): String = replInterpreter.run(dsl.dsl)

  override def registerPath(): Unit = {
    registerHandler(POST, "/seed", seed)
    registerHandler(POST, "/repl", repl)
  }
}
