package org.bugogre.crawler.parser.impl

import java.net.{MalformedURLException, URL}

import com.secer.elastic.model.{FetchItem, FieldSelector, IndexField, Page}
import com.secer.elastic.util.HashUtil
import org.bugogre.crawler.cache.URLCache
import org.bugogre.crawler.html.HtmlToMarkdown
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.url.UrlNormalizer
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}

import scala.collection.JavaConverters._

/**
 * Created by xiachen on 3/1/15.
 */
object HtmlPageParser {
  lazy val m = java.security.MessageDigest.getInstance("MD5")
  lazy val htmlToMarkdown = new HtmlToMarkdown

  def normalize(url: String): URL = UrlNormalizer.normalize(url)

  def hash(s: String): String = {
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }

  def parse(html: String): (Page, List[FetchItem]) = parse(Jsoup.parse(html), null)

  def filterHref(url: URL, target: String): Boolean = {
    try {
      val t: URL = new URL(target)
      url.getHost == t.getHost
    } catch {
      case e: MalformedURLException => false
    }
  }

  def filterFetchedItem(url: String, item: FetchItem): Boolean =
    !URLCache.FETCH_ITEM_CAHCE.containsKey(normalize(url).toString)

  def filterFetchItemByUrlRegex(url: String, regex: String): Boolean = {
    url.matches(regex)
  }

  def hrefs(doc: Document, item: FetchItem): List[FetchItem] = {
    for {
      href: String <- doc.select("a").asScala.toList
        .map(e => e.absUrl("href"))
        .filter(e => filterHref(item.url, e))
        .filter(e => filterFetchedItem(e, item))
        .filter(e => filterFetchItemByUrlRegex(e, item.urlRegex.getOrElse(".*")))
    } yield FetchItem(normalize(href), item.indexName, item.indexType, item.selectors, item.urlRegex)
  }


  def parse(doc: Document, item: FetchItem): (Page, List[FetchItem]) = {
    (Page(doc, item, HashUtil.hashString(doc.html), hash(item.url.toString), parseBySelector(doc, item.selectors)), hrefs(doc, item))
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
