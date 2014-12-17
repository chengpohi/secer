package org.bugogre.crawler.parser

import org.scalatest.FlatSpec

/**
 * Created by xiachen on 12/16/14.
 */
class HtmlParserTest extends FlatSpec{
  val htmlStr: String = "<html><head><title>First parse</title></head><body><p>Parsed HTML into a doc.</p></body></html>"
  "HtmlParser " should " parse successfully html by div " in {
    val htmlParser = new HtmlParser
    val html = htmlParser parse htmlStr
    assert(html.title === "First parse")
  }
}
