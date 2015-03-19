package org.bugogre.crawler.parser.impl

import org.bugogre.crawler.builder.WebBuilder
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/1/15.
 */
class HtmlPageParserTest extends FlatSpec{
  val web = WebBuilder.web
  val page =  HtmlPageParser.parse(web)._1
  "Html Page Parser " should "parse web" in{
    assert(page != null)
  }

  "Html Page Parser " should "hash" in{
    assert(page.md5 != null)
  }

  "Html Page Parser " should "get all hrefs" in{
    HtmlPageParser.hrefs(page.doc, page.fetchItem).map(u => println(u))
  }
}
