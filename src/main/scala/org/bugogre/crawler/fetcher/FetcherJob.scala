package org.bugogre.crawler.fetcher

import org.bugogre.crawler.httpclient._
import org.bugogre.crawler.config._

import java.util.concurrent.{Executors, ExecutorService}
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

import scala.util.{Success, Failure}
import scala.concurrent._

import org.slf4j.LoggerFactory

case class FetchItem(url: String)

class FetcherJob[T] extends Runnable {
  val fetchItems = new LinkedBlockingQueue[T]()

  def &(url: T): FetcherJob[T] = {
    fetchItems put url
    this
  }

  def START = new Thread(this).start

  def INPUT(url: T) = {
    &(url)
  }

  def STATUS = {
    println("FetchItems Size:" + fetchItems.size())
  }

  val LOG = LoggerFactory.getLogger(getClass().getName);

  def run() {
    implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.threads.getInt("fetcher")))

    println("Start Fetcher Job...")
    println("Threads: " + SecConfig.threads.getInt("fetcher"))

    while(true) {
      val url: T = fetchItems take

      val web: Future[Web[T]] = future {
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

}
