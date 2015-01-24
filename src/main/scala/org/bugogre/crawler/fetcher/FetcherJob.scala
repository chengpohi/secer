package org.bugogre.crawler.fetcher

import org.bugogre.crawler.httpclient._
import org.bugogre.crawler.config._

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

import org.bugogre.crawler.rule.Rule
import org.bugogre.crawler.url.FetchItem

import scala.util.{Success, Failure}
import scala.concurrent._

import org.slf4j.LoggerFactory

class FetcherJob extends Runnable {
  val fetchItems = new LinkedBlockingQueue[FetchItem]()
  lazy val rule = Rule(SecConfig.excludeUrlPatterns)

  def &(url: FetchItem): FetcherJob = {
    url.filterByRule(rule) match {
      case true =>
      case false => fetchItems put url
    }
    this
  }

  def START = new Thread(this).start()

  def INPUT(url: FetchItem) = {
    &(url)
  }

  def STATUS = {
    println("FetchItems Size:" + fetchItems.size())
  }

  def EXIT = {
      println("Fetch All Urls, Crawler Exist.")
      System.exit(0)
  }

  val LOG = LoggerFactory.getLogger(getClass.getName);

  def run() {
    println("Start Fetcher Job...")
    println("Fetcher Threads: " + SecConfig.threads.getInt("fetcher"))

    while(true) {
      val url: FetchItem = fetchItems take

      val web: Future[Web[FetchItem]] = future {
        WebFactory ==> url
      }

      web onComplete {
        case Success(w) => {
          LOG.info("URL: " + w.url + " get fnished. html length: " + w.html.length)
        }
        case Failure(t) => LOG.info("URL: " + url + " get failure. Error: " + t.getMessage)
      }
    }
  }
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.threads.getInt("fetcher")))
}
