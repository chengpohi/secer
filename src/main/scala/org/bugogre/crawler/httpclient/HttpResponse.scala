package org.bugogre.crawler.httpclient

import com.secer.elastic.model.FetchItem
import org.apache.http.HttpEntity
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory

case class Web(fetchItem: FetchItem, doc: Document) {
}

object HttpResponse {
  val LOG = LoggerFactory.getLogger(getClass.getName)

  def getEntity(url: String): HttpEntity = {
    null
  }

  def getEntityToStr(item: FetchItem): Web = {
    val url = item.url

    val request: HttpGet = new HttpGet(url.toString)
    val response: CloseableHttpResponse = HttpClientBuilder.create().build().execute(request)

    val doc = Jsoup.parse(EntityUtils.toString(response.getEntity))
    doc.setBaseUri(url.getProtocol + "://" + url.getHost)

    Web(item, doc)
  }

  def ==>(item: FetchItem): Web = {
    getEntityToStr(item)
  }
}
