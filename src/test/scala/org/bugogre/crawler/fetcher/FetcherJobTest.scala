package org.bugogre.crawler.fetcher

import org.scalatest.FlatSpec
import org.scalamock.scalatest.MockFactory

/**
 * Created by xiachen on 12/13/14.
 */
class FetcherJobTest extends FlatSpec with MockFactory{
  "FetchItems" should "succeed add urls" in {
    val fetcher = new FetcherJob[String]

    assert(fetcher.fetchItems.size() === 0)

    fetcher & "http://www.baidu.com"

    assert(fetcher.fetchItems.size() === 1)

    fetcher &("http://www.baidu.com")

    assert(fetcher.fetchItems.size() === 2)
  }

  "FetchItems" should "succeed add input urls" in {
    val fetcher = new FetcherJob[String]

    assert(fetcher.fetchItems.size() === 0)

    fetcher.INPUT("http://www.google.com")

    assert(fetcher.fetchItems.size() === 1)
  }
}
