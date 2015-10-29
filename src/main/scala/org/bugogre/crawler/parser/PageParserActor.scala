package org.bugogre.crawler.parser

import akka.actor.{Actor, Props}
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.PageIndexerActor
import org.bugogre.crawler.parser.impl.HtmlPageParser
import org.slf4j.LoggerFactory


/**
 * Page Parser
 * Created by xiachen on 12/16/14.
 */
class PageParserActor extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  val pageIndexer = context.actorOf(Props[PageIndexerActor], "PageIndexer")

  val pageParser = new HtmlPageParser(pageIndexer)

  def receive = {
    case str: String =>
    case web: Web =>
      LOG.info("Parse Url: " + web.fetchItem.url.toString)
      pageParser.asyncParse(sender(), web)
  }
}
