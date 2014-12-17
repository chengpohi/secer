package org.bugogre.crawler.parser

import org.bugogre.crawler.html.Html
import org.bugogre.crawler.url.Url
import org.jsoup.Jsoup

/**
 * Created by xiachen on 12/16/14.
 */
class HtmlParser {
  def parse(html: String): Html = {
    val doc = Jsoup.parse(html)
    Html(doc.title, doc, null)
  }

  def parse(html: String, url: Url): Html = {
    val doc = Jsoup.parse(html)
    Html(doc.title, doc, url)
  }
}
