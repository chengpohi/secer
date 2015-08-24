package org.bugogre.crawler.parser.impl

import org.bugogre.crawler.builder.WebBuilder
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/1/15.
 */
class HtmlPageParserActorTest extends FlatSpec {
  val web = WebBuilder.web
  val htmlPageParser = new HtmlPageParser(null, null)
  val page = htmlPageParser.parse(web)._1
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
    assert(htmlPageParser.hrefs(page.doc, page.fetchItem).nonEmpty)
  }

  "Html Page Parser " should "filter href by regex" in {
    urlFilter.foreach(t => {
      assert(htmlPageParser.filterFetchItemByUrlRegex(t._2, t._1) == t._3)
    })
  }
}
