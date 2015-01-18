package org.bugogre.crawler.parser

import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit, TestActorRef}
import org.bugogre.crawler.html.Page
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.parser.unit.HtmlParser
import org.bugogre.crawler.url.Url
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * Created by xiachen on 12/16/14.
 */
class HtmlParserTest(_system: ActorSystem) extends TestKit(_system)
with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {
  val parser = system.actorOf(Props[HtmlParser], "HtmlParser")
  val htmlStr: String = "<html><head><title>title</title></head><body><p>Parsed HTML into a doc.</p></body></html>"

  def this() = this(ActorSystem("MySpec"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "HtmlIndexer's indexUrl" must {
    "not be empty" in {
      parser ! Web[Url](Url("http://www.baidu.com"), htmlStr)
      expectMsgPF() {
        case page: Page =>  {
          assert(page.title == "title")
          assert(page.url == Url("http://www.baidu.com"))
        }
      }
    }
  }
}
