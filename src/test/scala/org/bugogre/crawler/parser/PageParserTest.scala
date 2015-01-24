package org.bugogre.crawler.parser

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.bugogre.crawler.html.Page
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.parser.unit.PageParser
import org.bugogre.crawler.url.FetchItem
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * Created by xiachen on 12/16/14.
 */
class PageParserTest(_system: ActorSystem) extends TestKit(_system)
with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {
  val parser = system.actorOf(Props[PageParser], "HtmlParser")
  val htmlStr: String = "<html><head><title>title</title></head><body><p>Parsed HTML into a doc.</p></body></html>"

  def this() = this(ActorSystem("PageParserTest"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "PageIndexer's indexUrl" must {
    "not be empty" in {
      parser ! Web[FetchItem](FetchItem("http://www.baidu.com"), htmlStr)
      expectMsgPF() {
        case page: Page =>  {
          assert(page.title == "title")
          assert(page.url == FetchItem("http://www.baidu.com"))
        }
      }
    }
  }
}
