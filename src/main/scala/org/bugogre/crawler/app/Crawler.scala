package org.bugogre.crawler.app

import java.util.concurrent.TimeUnit

import org.bugogre.crawler.fetcher._
import org.bugogre.crawler.url.Url

/**
 * author: chengpohi@gmail.com
 */
object Crawler extends FetcherJob{
  def main(args: Array[String]) {
    &(Url("http://www.baidu.com"))
    &(Url("http://www.baidu.com"))
    &(Url("http://www.baidu.com"))
    &(Url("http://www.baidu.com"))

    START

    TimeUnit.SECONDS.sleep(5)
    println("Hello Jack")
    &(Url("http://www.zhihu.com"))
  }
}
