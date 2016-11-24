package com.github.chengpohi.parser

import akka.actor.{Actor, ActorLogging, ActorSelection, Props}
import com.github.chengpohi.indexer.PageIndexerService
import com.github.chengpohi.model.FetchItem
import org.jsoup.nodes.Document


/**
  * Page Parser
  * Created by xiachen on 12/16/14.
  */
class PageParserService extends Actor with ActorLogging {
  val pageIndexer = context.actorOf(Props[PageIndexerService], "indexer")
  val fetcher: ActorSelection = context.actorSelection("/user/crawler/fetcher")
  val pageParser = new impl.HtmlPageParser

  def receive: Receive = {
    case (item: FetchItem, doc: Document) =>
      log.info("Parse item: " + item.id)
      val res = pageParser.parse(doc, item)
      pageIndexer ! res._1
      log.info(s"New Fetch Items Size: ${res._2.length}")
      res._2.foreach(f => {
        fetcher ! f
      })
  }
}
