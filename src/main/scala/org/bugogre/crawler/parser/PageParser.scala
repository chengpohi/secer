package org.bugogre.crawler.parser

import java.util.concurrent.Executors

import akka.actor.{Actor, Props}
import org.bugogre.crawler.config.SecConfig
import org.bugogre.crawler.fetcher.PageFetcherActor
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.PageIndexer
import org.bugogre.crawler.parser.impl.HtmlPageParser
import org.slf4j.LoggerFactory

import scala.concurrent._


/**
 * Page Parser
 * Created by xiachen on 12/16/14.
 */
class PageParser extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.MAX_THREADS))

  val pageIndexer = context.actorOf(Props[PageIndexer], "PageIndexer")
  val pageFetcher = context.actorOf(Props[PageFetcherActor], "PageFetcher")

  def receive = {
    case str: String =>
    case web: Web => {
      LOG.info("Parse Url: " + web.fetchItem.url.toString)
      asyncParse(web)
    }
  }

  def asyncParse(web: Web): Future[String] = {
    Future {
      blocking {
        val res = HtmlPageParser.parse(web)
        pageIndexer ! res._1

        LOG.info(s"Seeds size: ${res._2.length} url: ${web.fetchItem.url.toString}")

        pageFetcher ! res._2
        ""
      }
    }
  }
}
