package org.bugogre.crawler.app

import org.bugogre.crawler.fetcher._
import org.bugogre.crawler.config._

import java.util.concurrent.{Executors, ExecutorService}
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

/**
 * author: chengpohi@gmail.com
 */
object Crawler {
  def main(args: Array[String]) {
    val pool: ExecutorService = Executors.newFixedThreadPool(1)
    val fetchItems = new LinkedBlockingQueue[String]()
    fetchItems put "http://www.baidu.com"
    fetchItems put "http://www.baidu.com"
    fetchItems put "http://www.baidu.com"
    fetchItems put "http://www.baidu.com"
    fetchItems put "http://www.baidu.com"
    fetchItems put "http://www.baidu.com"
    fetchItems put "http://www.baidu.com"

    pool.execute(new FetcherJob(fetchItems))
    pool.shutdown()
  }
}
