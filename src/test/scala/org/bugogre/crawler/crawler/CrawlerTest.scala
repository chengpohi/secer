package org.bugogre.crawler.crawler

import org.bugogre.crawler.app.Crawler
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 1/11/15.
 */
class CrawlerTest  extends FlatSpec with MockFactory{
  val crawler = Crawler
  "Crawler" should " fetch url by input" in {
  }
}
