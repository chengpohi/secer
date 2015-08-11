package org.bugogre.crawler.httpclient

import com.secer.elastic.model.FetchItem
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.bugogre.crawler.filter.PageFilter
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

    val response = HttpClientBuilder.create().build()
      .execute(new HttpGet(item.asInstanceOf[FetchItem].url))

    Web(item, Jsoup.parse(EntityUtils.toString(response.getEntity)))
  }

  def ==>(item: FetchItem): Web = {
    getEntityToStr(item)
  }
}
