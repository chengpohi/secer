package org.bugogre.crawler.parser.impl

import org.bugogre.crawler.builder.WebBuilder
import org.bugogre.crawler.parser.impl.HtmlPageParser.filterFetchItemByUrlRegex
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/1/15.
 */
class HtmlPageParserTest extends FlatSpec {
  val web = WebBuilder.web
  val page = HtmlPageParser.parse(web)._1
  val urlFilter = List(
    (".*", "http://www.google.com", true),
    ("http://www.google.com/.*", "http://www.google.com", false),
    ("http://stackoverflow.com/.*", "http://stackoverflow.com/questions/4636610/how-to-pattern-match-using-regular-expression-in-scala", true),
    ("http://stackoverflow.com/questions/\\d+/.*", "http://stackoverflow.com/questions/4636610/how-to-pattern-match-using-regular-expression-in-scala", true)
  )
  "Html Page Parser " should "parse web" in {
    assert(page != null)
  }

  "Html Page Parser " should "hash" in {
    assert(page.md5 != null)
  }

  "Html Page Parser " should "get all hrefs" in {
    assert(HtmlPageParser.hrefs(page.doc, page.fetchItem).nonEmpty)
  }

  "Html Page Parser " should "filter href by regex" in {
    urlFilter.foreach(t => {
      assert(filterFetchItemByUrlRegex(t._1, t._2) == t._3)
    })
  }
}
