package org.bugogre.crawler.httpclient
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.bugogre.crawler.filter.PageFilter
import org.bugogre.crawler.url.FetchItem

import org.slf4j.LoggerFactory

case class Web(fetchItem: FetchItem, html: String) {
  def filter(): Boolean = {
    PageFilter.filterContent(fetchItem, html)
  }
}

object WebFactory {

  val LOG = LoggerFactory.getLogger(getClass.getName);

  def getEntity(url: String): HttpEntity = {
    null
  }

  def getEntityToStr(item: FetchItem): Web = {
    val response = new DefaultHttpClient()
      .execute(new HttpGet(item.asInstanceOf[FetchItem].url))

    Web(item, EntityUtils.toString(response.getEntity))
  }

  def ==>(item: FetchItem): Web = {
    getEntityToStr(item)
  }
}
