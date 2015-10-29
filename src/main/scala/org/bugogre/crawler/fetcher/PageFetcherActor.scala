package org.bugogre.crawler.fetcher


import akka.actor._
import com.secer.elastic.model.FetchItem
import com.typesafe.config.ConfigFactory
import org.bugogre.crawler.fetcher.impl.HtmlPageFetcher
import org.bugogre.crawler.parser.PageParserActor

object PageFetcherActor {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Crawler", ConfigFactory.load("fetcher"))
    system.actorOf(Props[PageFetcherActor], "pagefetcher")
  }
}

class PageFetcherActor extends Actor with ActorLogging {
  val pageParser = context.actorOf(Props[PageParserActor], "htmlParser")

  var fetchers = Map[String, ActorRef]()

  override def preStart(): Unit = {
  }

  override def postStop() = {
    log.info("Stopping parent Actor")
  }

  def receive = {
    case str: String =>
      pageParser ! str
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
      sender() ! s"${fetchItem.url.toString} has been sended to child fetcher to fetch."
    case _ => log.info("Object Exist.")
  }

  def createNewFetcher(fetchItem: FetchItem, parser: ActorRef): ActorRef = {
    val htmlPageFetcher = context.actorOf(Props(classOf[HtmlPageFetcher], parser, fetchItem))
    htmlPageFetcher
  }
}
