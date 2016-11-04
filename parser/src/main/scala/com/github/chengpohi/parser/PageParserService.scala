package com.github.chengpohi.parser

import akka.actor.{Actor, Props}
import com.github.chengpohi.indexer.PageIndexerService
import com.github.chengpohi.model.FetchItem
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory


/**
  * Page Parser
  * Created by xiachen on 12/16/14.
  */
class PageParserService extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  val pageIndexer = context.actorOf(Props[PageIndexerService], "page-indexer")
  val pageParser = new impl.HtmlPageParser(pageIndexer)

  def receive: Receive = {
    case (item: FetchItem, doc: Document) =>
      LOG.info("Parse Url: " + item.url)
      pageParser.asyncParse(sender(), (item, doc))
  }
}
