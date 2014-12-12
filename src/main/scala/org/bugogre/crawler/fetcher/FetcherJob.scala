package org.bugogre.crawler.fetcher

import org.bugogre.crawler.httpclient._
import org.bugogre.crawler.config._

import java.util.concurrent.{Executors, ExecutorService}
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

import scala.util.{Success, Failure}
import scala.concurrent._

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

case class FetchItem(url: String)

class FetcherJob extends Runnable {
  val fetchItems = new LinkedBlockingQueue[String]()

  def &(str: String) = fetchItems put str

  def START = this.run

  val LOG = LoggerFactory.getLogger(getClass().getName);

  def run() {
    implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.threads.getInt("fetcher")))

    println("Start Fetcher Job...")
    println("Threads: " + SecConfig.threads.getInt("fetcher"))

    while(true) {
      val url = fetchItems take
      val web: Future[Web] = future {
        WebFactory ==> url
      }

      web onComplete {
        case Success(w) => LOG.info("URL: " + w.url + " get fnished. html length: " + w.html.length)
        case Failure(t) => LOG.info("URL: " + url + " get failure. Error: " + t.getMessage)
      }
    }
  }
}
