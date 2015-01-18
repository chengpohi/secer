package org.bugogre.crawler.parser

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestActorRef}

/**
 * Created by xiachen on 12/16/14.
 */
object HtmlParserTest extends TestKit(ActorSystem("HtmlParserTest")){
  val htmlStr: String = "<html><head><title>title</title></head><body><p>Parsed HTML into a doc.</p></body></html>"
  val actorRef = TestActorRef(new HtmlParser)
  val actor = actorRef.underlyingActor
  val page = actor.parse(htmlStr)
  assert(page.title == "title")
  assert(page.url == null)
}
