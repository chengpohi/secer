package com.github.chengpohi.httpclient

import java.net.URL

import com.github.chengpohi.model._
import org.apache.http.HttpEntity
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory


object HttpResponse {
  val LOG = LoggerFactory.getLogger(getClass.getName)

  def getEntity(url: String): HttpEntity = {
    null
  }

  def getEntityToStr(item: FetchItem): Web = {
    val url = item.url

    val request: HttpGet = new HttpGet(url)
    val response: CloseableHttpResponse = HttpClientBuilder.create().build().execute(request)

    val doc = Jsoup.parse(EntityUtils.toString(response.getEntity))
    doc.setBaseUri(extractBaseUri(url))

    Web(item, doc)
  }

  def extractBaseUri(url: String): String = {
    val uri = new URL(url)
    s"${uri.getProtocol}://${uri.getHost}"
  }

  def ==>(item: FetchItem): Web = {
    getEntityToStr(item)
  }
}
