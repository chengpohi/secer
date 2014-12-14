package org.bugogre.crawler.app

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
  }
}
