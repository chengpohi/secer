package org.bugogre.crawler.parser.impl

import org.bugogre.crawler.html.Page
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.indexer.{FieldSelector, IndexField}
import org.bugogre.crawler.url.{UrlNormalizer, FetchItem}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import scala.collection.JavaConverters._
import scala.collection.mutable

/**
 * Created by xiachen on 3/1/15.
 */
object HtmlPageParser {
  lazy val m = java.security.MessageDigest.getInstance("MD5")

  def normalize(url: String) = UrlNormalizer.normalize(url)

  def hash(s: String): String = {
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }

  def parse(html: String): (Page, mutable.Buffer[FetchItem]) = parse(html, null)

  def hrefs(doc: Document, item: FetchItem): mutable.Buffer[FetchItem] = {
    for (href <- doc.select("a").asScala) yield FetchItem(normalize(href.attr("abs:href")),
      item.indexName, item.indexType, item.selectors)
  }

  def parse(html: String, item: FetchItem): (Page, mutable.Buffer[FetchItem]) = {
    val doc = Jsoup.parse(html)
    (Page(doc, item, hash(html), hash(item.url), parseBySelector(doc, item.selectors)), hrefs(doc, item))
  }

  def selectBySelector(doc: Document, selector: String): String = {
    doc.select(selector).first().html()
  }

  def parseBySelector(doc: Document, fieldSelectors: List[FieldSelector]): List[IndexField] = {
    for (fieldSelector <- fieldSelectors) yield IndexField(fieldSelector.field, selectBySelector(doc, fieldSelector.selector))
  }

  def parse(web: Web[FetchItem]): (Page, mutable.Buffer[FetchItem]) = parse(web.html, web.fetchItem)
}