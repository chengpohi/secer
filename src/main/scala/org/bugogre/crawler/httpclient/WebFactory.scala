package org.bugogre.crawler.httpclient
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.bugogre.crawler.url.Url

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

case class Web[T](url: T, html: String)

object WebFactory {

  val LOG = LoggerFactory.getLogger(getClass.getName);

  def getEntity(url: String): HttpEntity = {
    null
  }

  def getEntityToStr(url: Url): Web[Url] = {
    val response = new DefaultHttpClient()
      .execute(new HttpGet(url.asInstanceOf[Url].url))

    Web(url, EntityUtils.toString(response.getEntity))
  }

  def ==>(url: Url): Web[Url] = {
    getEntityToStr(url)
  }
}
