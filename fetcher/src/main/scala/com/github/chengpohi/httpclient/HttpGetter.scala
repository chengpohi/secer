package com.github.chengpohi.httpclient

import java.net.URL

import com.github.chengpohi.model._
import org.apache.http.HttpEntity
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory


object HttpGetter {
  val logger = LoggerFactory.getLogger(getClass.getName)

  def getEntityToWeb(item: FetchItem): (FetchItem, Document) = {
    val url = item.url

    val request: HttpGet = new HttpGet(url)
    val response: CloseableHttpResponse = HttpClientBuilder.create().build().execute(request)

    val doc = Jsoup.parse(EntityUtils.toString(response.getEntity))
    doc.setBaseUri(extractBaseUri(url))
    (item, doc)
  }

  def extractBaseUri(url: String): String = {
    val uri = new URL(url)
    s"${uri.getProtocol}://${uri.getHost}"
  }

  def connect(item: FetchItem): (FetchItem, Document) = getEntityToWeb(item)
}
