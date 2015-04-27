package org.bugogre.crawler.httpclient

import org.apache.http.HttpEntity
import org.bugogre.crawler.filter.PageFilter
import org.bugogre.crawler.url.FetchItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory

case class Web(fetchItem: FetchItem, doc: Document) {
  def filter(): Boolean = {
    PageFilter.filterContent(fetchItem, doc.html())
  }
}

object HttpResponse {
  val LOG = LoggerFactory.getLogger(getClass.getName)

  def getEntity(url: String): HttpEntity = {
    null
  }

  def getEntityToStr(item: FetchItem): Web = {
    val doc = Jsoup.connect(item.url).get()

    Web(item, doc)
  }

  def ==>(item: FetchItem): Web = {
    getEntityToStr(item)
  }
}
