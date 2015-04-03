package org.bugogre.crawler.url.filter

import org.scalatest.FlatSpec

/**
 * Created by xiachen on 4/3/15.
 */
class UrlFilterTest extends FlatSpec{
  "UrlFilter " should " return false when url not timeout" in {
    println(UrlFilter.filterByTime("http://stackoverflow.com/", "turing", "stackoverflow"))
    assert(!UrlFilter.filterByTime("http://stackoverflow.com/", "turing", "stackoverflow"))
  }
}
