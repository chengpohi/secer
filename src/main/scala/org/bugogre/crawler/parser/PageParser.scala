package org.bugogre.crawler.parser

import akka.actor.{Actor, Props}
import org.bugogre.crawler.fetcher.PageFetcher
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.PageIndexer
import org.bugogre.crawler.parser.impl.HtmlPageParser
import org.slf4j.LoggerFactory


/**
 * Created by xiachen on 12/16/14.
 */
class PageParser extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  val pageIndexer = context.actorOf(Props[PageIndexer], "PageIndexer")
  val webFetcher = context.actorOf(Props[PageFetcher], "WebFetcher")

  def receive = {
    case str: String =>
    case web: Web => parse(web)
  }

  def parse(web: Web): Unit = {
    web.filter() match {
      case false =>
        LOG.info("Parse Url: " + web.fetchItem.url)
        val res = HtmlPageParser.parse(web)
        pageIndexer ! res._1
        sender() ! res._2
      case true => LOG.info(web.fetchItem.url + " has parsed and indexed, so ignore it.")
    }
  }
}
