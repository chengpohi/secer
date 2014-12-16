package org.bugogre.crawler.filter

import org.bugogre.crawler.rule.Rule
import org.bugogre.crawler.url.Url
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 12/16/14.
 */
class UrlFilterTest extends FlatSpec{
  "Url Filter " should " filter url by rules" in {
    val urls = List(Url("http://www.baidu.com"), Url("http://www.zhihu.com"), Url("http://www.zhihu.com"))

    val rules = List(".*zhihu.*".r, ".*baidu.*".r)
    val rule = Rule(rules)
    urls.filter(url => !url.filterByRule(rule)).map(u =>
      assert(u.filterByRule(rule) === false)
    )
  }
}
