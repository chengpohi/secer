package org.bugogre.crawler.fetcher

import org.bugogre.crawler.httpclient._
import org.bugogre.crawler.config._

import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

import org.bugogre.crawler.url.Url

import scala.util.{Success, Failure}
import scala.concurrent._

import org.slf4j.LoggerFactory

class FetcherJob extends Runnable {
  val fetchItems = new LinkedBlockingQueue[Url]()

  def &(url: Url): FetcherJob = {
    fetchItems put url
    this
  }

  def START = new Thread(this).start()

  def INPUT(url: Url) = {
    &(url)
  }

  def STATUS = {
    println("FetchItems Size:" + fetchItems.size())
  }

  val LOG = LoggerFactory.getLogger(getClass.getName);

  def run() {
    println("Start Fetcher Job...")
    println("Threads: " + SecConfig.threads.getInt("fetcher"))

    while(true) {
      val url: Url = fetchItems take

      val web: Future[Web[Url]] = future {
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
