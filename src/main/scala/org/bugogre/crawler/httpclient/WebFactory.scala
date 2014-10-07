package org.bugogre.crawler.httpclient
import org.apache.http.HttpEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils

object WebFactory {
  def getEntity(url: String): HttpEntity = {
    var httpclient = new DefaultHttpClient()
    var httpget = new HttpGet(url)
    var response = httpclient.execute(httpget)
    val entity = response.getEntity
    entity
  }

  def getEntityToStr(url: String): String = {
    var httpclient = new DefaultHttpClient()
    var httpget = new HttpGet(url)
    var response = httpclient.execute(httpget)
    val entity = response.getEntity
    EntityUtils.toString(entity)
  }

}
