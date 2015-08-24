package org.bugogre.crawler.parser

import java.net.URL

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.secer.elastic.model.{FieldSelector, FetchItem}
import org.bugogre.crawler.httpclient.Web
import org.jsoup.Jsoup
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * Created by xiachen on 12/16/14.
 */
class PageParserActorTest(_system: ActorSystem) extends TestKit(_system)
with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {
  val parser = system.actorOf(Props[PageParserActor], "HtmlParser")
  val htmlStr: String = "<html><head><title>title</title></head><body><p>Parsed HTML into a doc.</p></body></html>"
  val fetchItem = FetchItem(new URL("http://www.zhihu.com"), "ask", "zhihu",
    List(FieldSelector("_title", "title"), FieldSelector("_content", "body")))
  val web = Web(fetchItem, Jsoup.parse(htmlStr))

  def this() = this(ActorSystem("PageParserTest"))

  override def afterAll {
  }
}
