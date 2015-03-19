package org.bugogre.crawler.fetcher

import java.util.concurrent.Executors

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import org.bugogre.crawler.config._
import org.bugogre.crawler.fetcher.impl.HtmlPageFetcher
import org.bugogre.crawler.parser.PageParser
import org.bugogre.crawler.rule.Rule
import org.bugogre.crawler.url.FetchItem
import org.slf4j.LoggerFactory

import scala.collection.mutable
import scala.concurrent.{Future, blocking}
import scala.concurrent._

object PageFetcher {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Crawler", ConfigFactory.load("fetcher"))
    system.actorOf(Props[PageFetcher], "pagefetcher")
  }
}

class PageFetcher extends Actor {

  lazy val rule = Rule(SecConfig.EXCLUDE_URL_PATTERNS)

  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  val pageParser = context.actorOf(Props[PageParser], "htmlParser")

  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))

  override def preStart(): Unit = {
  }

  def receive = {
    case str: String => {
      pageParser ! str
    }
    case fetchItem: FetchItem => {
      fetchItem.filterByRule(rule) match {
        case false => {
          LOG.info("Fetch Url: " + fetchItem.url)
          pageParser ! HtmlPageFetcher.fetch(fetchItem)
          sender() ! fetchItem.url + " fetch finished."
        }
        case _ => {
        }
      }
    }
    case fetchItems: mutable.Buffer[FetchItem] => {
      fetchItems.filter(_.url.length != 0).map(fetchItem => {
        Future {
          blocking {
            fetchItem.filterByRule(rule) match {
              case false => {
                LOG.info("Fetch Url: " + fetchItem.url)
                pageParser ! HtmlPageFetcher.fetch(fetchItem)
                sender() ! fetchItem.url + " fetch finished."
              }
              case _ => {
              }
            }
          }
        }
      })
    }
  }
}
