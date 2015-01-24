package org.bugogre.crawler.url

import org.bugogre.crawler.exception.UrlIllegalException
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 12/13/14.
 */
class FetchItemTest extends FlatSpec{
  "Url case class " should "contain url" in {
    val url = FetchItem("http://www.baidu.com")
    assert(url.url === "http://www.baidu.com")
  }

  "Url case class " should " throw url Illegally" in {
    val thrown = intercept[UrlIllegalException] {
      FetchItem("www.baidu.com")
    }

    assert(thrown.msg === "Url is Illegal")
  }
}
