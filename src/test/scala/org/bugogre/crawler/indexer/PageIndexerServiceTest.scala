package org.bugogre.crawler.indexer

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * Created by xiachen on 1/18/15.
 */

class PageIndexerServiceTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {
  val indexer = system.actorOf(Props[PageIndexerService], "PageIndexer")

  def this() = this(ActorSystem("PageIndexerServiceSpec"))

  override def afterAll(): Unit = Await.ready(system.terminate(), Duration.Inf)
}
