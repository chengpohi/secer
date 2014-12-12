package org.bugogre.crawler.app

import org.bugogre.crawler.fetcher._
import org.bugogre.crawler.config._

import java.util.concurrent.{Executors, ExecutorService}
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

/**
 * author: chengpohi@gmail.com
 */
object Crawler extends FetcherJob{
  def main(args: Array[String]) {
    &("http://www.baidu.com")
    &("http://www.baidu.com")
    &("http://www.baidu.com")
    &("http://www.baidu.com")
    START
  }
}
