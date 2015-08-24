package org.bugogre.crawler.parser

import akka.actor.{Actor, Props}

import org.bugogre.crawler.fetcher.PageFetcherActor
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.PageIndexer
import org.bugogre.crawler.parser.impl.HtmlPageParser

import org.slf4j.LoggerFactory


/**
 * Page Parser
 * Created by xiachen on 12/16/14.
 */
class PageParser extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  val pageIndexer = context.actorOf(Props[PageIndexer], "PageIndexer")

  val pageFetcher = context.actorOf(Props[PageFetcherActor], "PageFetcher")

  val pageParser = new HtmlPageParser(pageFetcher, pageIndexer)

  def receive = {
    case str: String =>
    case web: Web =>
      LOG.info("Parse Url: " + web.fetchItem.url.toString)
      pageParser.asyncParse(web)
  }
}
