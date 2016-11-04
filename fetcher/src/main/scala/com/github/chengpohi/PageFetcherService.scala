package com.github.chengpohi

import akka.actor._
import com.github.chengpohi.model.FetchItem
import com.github.chengpohi.parser.PageParserService
import com.typesafe.config.ConfigFactory

object PageFetcherService {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Crawler", ConfigFactory.load("fetcher"))
    system.actorOf(Props[PageFetcherService], "page-fetcher")
  }
}

class PageFetcherService extends Actor with ActorLogging {
  val pageParser = context.actorOf(Props[PageParserService], "html-parser")

  var fetchers = Map[String, ActorRef]()

  def receive: Receive = {
    case fetchItem: FetchItem =>
      val fetcherName: String = buildFetcherName(fetchItem)
      val fetcher = fetchers.getOrElse(fetcherName, buildNewFetcher(fetcherName, fetchItem, pageParser))
      fetcher ! fetchItem
      sender() ! s"${fetchItem.url} has been sended to child fetcher to fetch."
    case _ => log.info("Unknown Fetch Item")
  }

  def buildFetcherName(fetchItem: FetchItem): String = {
    s"""${fetchItem.indexName}-${fetchItem.indexType}"""
  }

  def buildNewFetcher(fetcherName: String, fetchItem: FetchItem, parser: ActorRef): ActorRef = {
    val fetcher = context.actorOf(Props(classOf[impl.HtmlPageFetcher], parser, fetchItem))
    fetchers += fetcherName -> fetcher
    fetcher
  }
}
