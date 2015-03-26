package org.bugogre.crawler.html

import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/26/15.
 */
class HtmlToMarkdownTest extends FlatSpec {
  val html = "<h1>Hello</h1><hr><h1>Hello</h1>" +
    "<div>World</div>" +
    "<h1>Hello</h1><h2>Hello</h2>" +
    "<p>Hello World</p>" +
    "<a href=\"http://www.baidu.com\">baidu</a>"
  val htmlToMarkDown = new HtmlToMarkdown

  "HtmlToMarkDown" should " parse header correctly" in {
    val result = htmlToMarkDown.parse(html)
    assert(result.contains("#"))
  }

  println(htmlToMarkDown.parse(html))
  "HtmlToMarkDown" should " parse hr correctly" in {
    val result = htmlToMarkDown.parse(html)
    assert(result.contains(htmlToMarkDown.getHr().trim))
  }
}
