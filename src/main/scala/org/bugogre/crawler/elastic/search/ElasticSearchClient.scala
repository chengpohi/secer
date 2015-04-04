package org.bugogre.crawler.elastic.search

import org.bugogre.crawler.elastic.ElasticClientConnector

import com.sksamuel.elastic4s.ElasticDsl._
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.search.SearchHit

/**
 * Created by xiachen on 3/19/15.
 */
object ElasticSearchClient {
  lazy val client = ElasticClientConnector.client
  lazy val m = java.security.MessageDigest.getInstance("MD5")

  def hash(s: String): String = {
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(1, m.digest()).toString(16)
  }

  def searchCall(indexName: String, indexType: String, field: String, key: String): SearchResponse =
    client.execute { search in indexName -> indexType query s"$field:$key" }.await

  def comparePage(indexName: String, indexType: String, html: String, url: String): SearchResponse =
    client.execute {
      search in indexName -> indexType query {
        bool {
          must (
            termQuery("_md5", hash(html)),
            termQuery("_urlMd5", hash(url))
          )
        }
      }
    }.await

  def searchUrl(indexName: String, indexType: String, key: String): SearchHit = {
    val hits = searchCall(indexName, indexType, "_urlMd5", hash(key)).getHits
    hits.getTotalHits match {
      case x: Long if x > 0 => hits.getAt(0)
      case _ => null
    }
  }
}
