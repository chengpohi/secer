package org.bugogre.crawler.html

import org.bugogre.crawler.builder.HtmlPageBuilder
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/26/15.
 */
class HtmlToMarkdownTest extends FlatSpec {
  val html = HtmlPageBuilder.HTML_MARKDOWN
  val htmlToMarkDown = new HtmlToMarkdown

  "HtmlToMarkDown" should " parse header correctly" in {
    val result = htmlToMarkDown.parse(html)
    assert(result.contains("#"))
  }

  println(htmlToMarkDown.parse(html))
  "HtmlToMarkDown" should " parse hr correctly" in {
    val result = htmlToMarkDown.parse(html)
    assert(result.contains(htmlToMarkDown.getHr.trim))
  }
}
