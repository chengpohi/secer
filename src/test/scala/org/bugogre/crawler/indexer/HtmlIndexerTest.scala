package org.bugogre.crawler.indexer

import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.{TestKit, ImplicitSender}
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

/**
 * Created by xiachen on 1/18/15.
 */

class HtmlIndexerTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {
  val indexer = system.actorOf(Props[HtmlIndexer], "HtmlIndexer")

  def this() = this(ActorSystem("MySpec"))
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "HtmlIndexer's indexUrl" must {
    "not be empty" in {
      val result = indexer ! "indexUrl"
      expectMsgPF() {
        case str: String => ()
      }
    }
  }
}
