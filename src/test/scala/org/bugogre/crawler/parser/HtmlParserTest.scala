package org.bugogre.crawler.parser

import org.scalatest.FlatSpec
import org.bugogre.crawler.parser.HtmlParser

/**
 * Created by xiachen on 12/16/14.
 */
class HtmlParserTest extends FlatSpec{
  val htmlStr: String = ""
  "HtmlParser " should " parse successfully html by div " in {
    val htmlParser = new HtmlParser
    val html = htmlParser % htmlStr
    assert(html.title.length === 0)
  }
}
