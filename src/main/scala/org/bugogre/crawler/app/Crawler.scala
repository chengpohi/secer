package org.bugogre.crawler.app

import java.util.concurrent.TimeUnit

import org.bugogre.crawler.fetcher._
import org.bugogre.crawler.url.Url
import org.bugogre.crawler.parser.HtmlParser


/**
 * author: chengpohi@gmail.com
 */
object Crawler{
  def main(args: Array[String]) {
    akka.Main.main(Array(classOf[CrawlerRunner].getName))
  }
}
