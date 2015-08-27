package org.bugogre.crawler.fetcher

import org.bugogre.crawler.fetcher.impl.HtmlPageFetcher
import org.scalatest.FlatSpec

/**
 * seccrawler
 * Created by chengpohi on 8/26/15.
 */
class HtmlPageFetcherTest  extends FlatSpec {
  val htmlPageFetcher = new HtmlPageFetcher(null)
  val urlFilter = List(
    (".*", "http://www.google.com", true),
    ("http://www.google.com/.*", "http://www.google.com", false),
    ("http://stackoverflow.com/.*", "http://stackoverflow.com/questions/4636610/how-to-pattern-match-using-regular-expression-in-scala", true),
    ("http://stackoverflow.com/questions/\\d+/.*", "http://stackoverflow.com/questions/4636610/how-to-pattern-match-using-regular-expression-in-scala", true)
  )


  "Html Page Fetcher " should "filter href by regex" in {
    urlFilter.foreach(t => {
      assert(htmlPageFetcher.filterFetchItemByUrlRegex(t._2, t._1) == t._3)
    })
  }}
