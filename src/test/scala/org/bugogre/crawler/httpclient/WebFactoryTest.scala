package org.bugogre.crawler.httpclient

import org.bugogre.crawler.url.Url
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 12/13/14.
 */
class WebFactoryTest extends FlatSpec{
  "Crawl Url " should " successful" in {
    val webPage: Web[Url] =
      WebFactory ==> Url("http://www.zhihu.com")
    assert(webPage.html.length != 0 )
  }
}
