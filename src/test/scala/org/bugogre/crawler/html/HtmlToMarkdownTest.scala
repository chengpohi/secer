package org.bugogre.crawler.html

import org.bugogre.crawler.builder.HtmlPageBuilder
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/26/15.
 */
class HtmlToMarkdownTest extends FlatSpec {
  val html = HtmlPageBuilder.HTML_MARKDOWN
  val stackOverflowHtml = "<div class=\"post-text\" itemprop=\"text\">\n\n        <p>Consider the following 2 alternate APIs:</p>\n\n<pre class=\"lang-java prettyprint prettyprinted\"><code><span class=\"kwd\">void</span><span class=\"pln\"> method</span><span class=\"pun\">(</span><span class=\"typ\">List</span><span class=\"pun\">&lt;?&gt;</span><span class=\"pln\"> list</span><span class=\"pun\">)</span><span class=\"pln\">\n</span><span class=\"pun\">&lt;</span><span class=\"pln\">T</span><span class=\"pun\">&gt;</span><span class=\"pln\"> </span><span class=\"kwd\">void</span><span class=\"pln\"> method</span><span class=\"pun\">(</span><span class=\"typ\">List</span><span class=\"pun\">&lt;</span><span class=\"pln\">T</span><span class=\"pun\">&gt;</span><span class=\"pln\"> list</span><span class=\"pun\">)</span></code></pre>\n\n<p>I know that their internal implementation will have many differences to deal with. such as List&lt;'?&gt; wont be able to write into the list etc.</p>\n\n<p>Also, in my knowledge List&lt;'?&gt; will allow any parameterized type with List as base type. So will be the case with List&lt;'T&gt; also.</p>\n\n<p>Can anybody tell me if there is any difference at all in what kinds of inputs these 2 APIs will accept.(NOT the 2 APIs internal implementation differences)</p>\n\n<p>Thanks in advance.</p>\n\n    </div>"
  val comment = "<div style=\"display: block;\" class=\"comment-body\">\n                <span class=\"comment-copy\">I believe they'll accept exactly the same input. The limitations of <code>&lt;?&gt;</code> only appear when you try to work with the list and the type is relevant for some reason.</span>\n                –&nbsp;\n                    <a href=\"/users/2382246/blalasaadri\" title=\"2602 reputation\" class=\"comment-user\">blalasaadri</a>\n                <span class=\"comment-date\" dir=\"ltr\"><a class=\"comment-link\" href=\"#comment46873152_29343301\"><span title=\"2015-03-30 10:03:45Z\" class=\"relativetime-clean\">20 mins ago</span></a></span>\n                                                                            </div>"
  val htmlToMarkDown = new HtmlToMarkdown

  //println(htmlToMarkDown.parse(stackOverflowHtml))
  println(htmlToMarkDown.parse(comment))
  "HtmlToMarkDown" should " parse header correctly" in {
    val result = htmlToMarkDown.parse(html)
    assert(result.contains("#"))
  }

  "HtmlToMarkDown" should " parse hr correctly" in {
    val result = htmlToMarkDown.parse(html)
    assert(result.contains(htmlToMarkDown.getHr.trim))
  }
}
