package org.bugogre.crawler.app

import org.bugogre.crawler.fetcher._

/**
 * author: chengpohi@gmail.com
 */
object Crawler extends FetcherJob[String]{
  def main(args: Array[String]) {
    &("http://www.baidu.com")
    &("http://www.baidu.com")
    &("http://www.baidu.com")
    &("http://www.baidu.com")

    START

  }
}
