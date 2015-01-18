package org.bugogre.crawler.indexer

import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.{TestKit, ImplicitSender}
import org.bugogre.crawler.html.Page
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

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

  "PageIndexer index" must {
    " page to elasticsearch" in {
      indexer ! Page("indexTitle", null, null)
      expectMsg("page index")
    }
  }
}
