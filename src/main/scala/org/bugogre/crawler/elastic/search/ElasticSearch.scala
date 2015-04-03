package org.bugogre.crawler.elastic.search

import org.bugogre.crawler.elastic.ElasticClientConnector

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._

/**
 * Created by xiachen on 3/19/15.
 */
object ElasticSearch {
  lazy val client = ElasticClientConnector.client
  lazy val m = java.security.MessageDigest.getInstance("MD5")

  def hash(s: String): String = {
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }

  def search(indexName: String, indexType: String, field: String, key: String): String = {
    client execute {
      search in indexName -> indexType query "field:tony"
    }
  }

  def searchUrl(indexName: String, indexType: String, key: String): String = {
    search(indexName, indexType, "_urlMd5", hash(key))
  }
}
