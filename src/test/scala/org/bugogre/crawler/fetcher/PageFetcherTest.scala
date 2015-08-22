package org.bugogre.crawler.fetcher

import java.net.URL

import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import com.secer.elastic.model.{FieldSelector, FetchItem}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike, Matchers}

/**
 * Created by xiachen on 2/28/15.
 */
class PageFetcherTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
with WordSpecLike with Matchers with BeforeAndAfterAll {
  val pageFetcher = system.actorOf(Props[PageFetcher], "PageFetcher")

  def this() = this(ActorSystem("PageFetcherSpec"))

  override def afterAll {
  }

  val fetchItem = FetchItem(new URL("http://www.zhihu.com"), "ask", "zhihu",
    List(FieldSelector("_title", "title"), FieldSelector("_content", "body")))

  "Page Fetcher " must {
    "fetch url" in {
      pageFetcher ! fetchItem
    }
  }
}
