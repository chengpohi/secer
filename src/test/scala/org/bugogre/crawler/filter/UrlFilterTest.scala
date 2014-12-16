package org.bugogre.crawler.filter

import org.bugogre.crawler.rule.Rule
import org.bugogre.crawler.url.Url
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 12/16/14.
 */
class UrlFilterTest extends FlatSpec{
  "Url Filter " should " filter url by rules" in {
    val url = Url("http://www.zhihu.com")

    val rules = List{".*zhihu.*".r}
    val rule = Rule(rules)
    assert(url.filterByRule(rule) === true)
  }
}
