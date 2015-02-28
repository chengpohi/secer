package org.bugogre.crawler.indexer

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * Created by xiachen on 1/18/15.
 */

class PageIndexerTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {
  val indexer = system.actorOf(Props[PageIndexer], "PageIndexer")

  def this() = this(ActorSystem("PageIndexerSpec"))
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "PageIndexer's indexUrl" must {
    "not be empty" in {
      indexer ! "indexUrl"
      expectMsgPF() {
        case str: String => ()
      }
    }
  }

}
