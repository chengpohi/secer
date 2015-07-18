package org.bugogre.crawler.fetcher

import java.util.concurrent.Executors

import akka.actor.{Actor, ActorSystem, Props}
import com.secer.elastic.model.FetchItem
import com.typesafe.config.ConfigFactory
import org.bugogre.crawler.config._
import org.bugogre.crawler.fetcher.impl.HtmlPageFetcher
import org.bugogre.crawler.parser.PageParser
import org.slf4j.LoggerFactory

import scala.concurrent.{Future, blocking, _}

object PageFetcher {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Crawler", ConfigFactory.load("fetcher"))
    system.actorOf(Props[PageFetcher], "pagefetcher")
  }
}

class PageFetcher extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  val pageParser = context.actorOf(Props[PageParser], "htmlParser")

  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.MAX_THREADS))

  override def preStart(): Unit = {
  }

  def fetch(fetchItem: FetchItem): Unit = {
    LOG.info("Fetch Url: " + fetchItem.url)
    pageParser ! HtmlPageFetcher.fetch(fetchItem)
    sender() ! fetchItem.url + " fetch finished."
  }

  def asyncFetch(fetchItem: FetchItem) = {
    Future {
      blocking {
        fetchItem.filterOrFetch(fetch)
      }
    }
  }

  def receive = {
    case str: String =>
      pageParser ! str
    case fetchItem: FetchItem => asyncFetch(fetchItem)
    case fetchItems: List[_] =>
      fetchItems.asInstanceOf[List[FetchItem]]
        .filter(_.url.length != 0)
        .map(fetchItem => asyncFetch(fetchItem))
  }
}
