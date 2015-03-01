package org.bugogre.crawler.parser

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.FieldSelector
import org.bugogre.crawler.parser.PageParser
import org.bugogre.crawler.url.FetchItem
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * Created by xiachen on 12/16/14.
 */
class PageParserTest(_system: ActorSystem) extends TestKit(_system)
with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {
  val parser = system.actorOf(Props[PageParser], "HtmlParser")
  val htmlStr: String = "<html><head><title>title</title></head><body><p>Parsed HTML into a doc.</p></body></html>"
  val fetchItem = FetchItem("http://www.zhihu.com", "ask", "zhihu",
    List(FieldSelector("_title", "title"), FieldSelector("_content", "body")))
  val web = Web(fetchItem, htmlStr)

  def this() = this(ActorSystem("PageParserTest"))

  override def afterAll {
  }

  "PageParser " must {
    "parse page" in {
      parser ! web
      expectMsgPF() {
        case str: String => _system.shutdown()
      }
    }
  }
}
