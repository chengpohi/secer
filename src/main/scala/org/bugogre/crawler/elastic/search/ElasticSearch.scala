package org.bugogre.crawler.elastic.search

import org.bugogre.crawler.elastic.ElasticClientConnector

/**
 * Created by xiachen on 3/19/15.
 */
object ElasticSearch {
  lazy val client = ElasticClientConnector.client
  def search(indexName: String, indexType: String, field: String, key: String): String = {
    null
  }
}
