package org.bugogre.crawler.parser.impl

import org.bugogre.crawler.html.{HtmlToMarkdown, Page}
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.{FieldSelector, IndexField}
import org.bugogre.crawler.url.{FetchItem, UrlNormalizer}
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}

import scala.collection.JavaConverters._

/**
 * Created by xiachen on 3/1/15.
 */
object HtmlPageParser {
  lazy val m = java.security.MessageDigest.getInstance("MD5")
  lazy val htmlToMarkdown = new HtmlToMarkdown

  def normalize(url: String) = UrlNormalizer.normalize(url)

  def hash(s: String): String = {
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }

  def parse(html: String): (Page, List[FetchItem]) = parse(Jsoup.parse(html), null)

  def hrefs(doc: Document, item: FetchItem): List[FetchItem] = {
    for (href: Element  <- doc.select("a").asScala.toList) yield FetchItem(normalize(href.attr("abs:href")),
      item.indexName, item.indexType, item.selectors)
  }

  def parse(doc: Document, item: FetchItem): (Page, List[FetchItem]) = {
    (Page(doc, item, hash(doc.html), hash(item.url), parseBySelector(doc, item.selectors)), hrefs(doc, item))
  }

  def selectBySelector(doc: Document, selector: String): String = {
    doc.select(selector).first() match {
      case el: Element => el.html()
      case null => ""
    }
  }

  def parseBySelector(doc: Document, fieldSelectors: List[FieldSelector]): List[IndexField] = {
    for (fieldSelector <- fieldSelectors) yield IndexField(fieldSelector.field,
      htmlToMarkdown.parse(selectBySelector(doc, fieldSelector.selector)))
  }

  def parse(web: Web): (Page, List[FetchItem]) = parse(web.doc, web.fetchItem)
}
