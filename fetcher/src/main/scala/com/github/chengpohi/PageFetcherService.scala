package com.github.chengpohi

import akka.actor._
import com.github.chengpohi.impl.HtmlPageFetcher
import com.github.chengpohi.model.FetchItem
import com.github.chengpohi.parser.PageParserService
import com.typesafe.config.ConfigFactory
import org.jsoup.nodes.Document

object PageFetcherService {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Crawler", ConfigFactory.load("fetcher"))
    system.actorOf(Props[PageFetcherService], "page-fetcher")
  }
}

class PageFetcherService extends Actor with ActorLogging {
  val pageParser = context.actorOf(Props[PageParserService], "parser")
  val fetcher = new HtmlPageFetcher()

  def receive: Receive = {
    case fetchItem: FetchItem if fetcher.filter(fetchItem) =>
      val result: Option[(FetchItem, Document)] = fetcher.fetch(fetchItem)
      log.info("fetch url: " + fetchItem.url)
      log.info("fetch item: " + fetchItem.id)
      result match {
        case Some(r) =>
          pageParser ! r
        case None =>
          log.warning("fetch fail: " + fetchItem.id)
      }

    case fetchItem: FetchItem =>
      log.info("item has been fetched: " + fetchItem.id)
    case _ => log.warning("Unknown Fetch Item")
  }
}
