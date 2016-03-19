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

  override def preStart(): Unit = {
  }

  override def postStop() = {
    log.info("Stopping parent Actor")
  }

  def receive = {
    case fetchItem: FetchItem =>
      val fetcherName: String = s"""${fetchItem.indexName}-${fetchItem.indexType}"""
      fetchers.get(fetcherName) match {
        case None =>
          val fetcher = createNewFetcher(fetchItem, pageParser)
          fetchers += fetcherName -> fetcher
          log.info("create a new fetcher with: " + fetcherName)
          fetcher ! fetchItem
        case actorRef: Option[ActorRef] =>
          log.info("continue fetcher: " + fetcherName)
          actorRef.get ! fetchItem
      }
      sender() ! s"${fetchItem.url} has been sended to child fetcher to fetch."
    case _ => log.info("Object Exist.")
  }

  def createNewFetcher(fetchItem: FetchItem, parser: ActorRef): ActorRef = {
    val htmlPageFetcher = context.actorOf(Props(classOf[impl.HtmlPageFetcher], parser, fetchItem))
    htmlPageFetcher
  }
}
