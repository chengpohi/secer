package org.bugogre.crawler.filter

import com.secer.elastic.model.FetchItem
import com.secer.elastic.search.ElasticSearchClient
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.indices.IndexMissingException

/**
 * Created by xiachen on 4/4/15.
 */
object PageFilter {
  def filterContent(fetchItem: FetchItem, html: String): Boolean = {
    try {
      ElasticSearchClient.comparePage(fetchItem.indexName, fetchItem.indexType, html, fetchItem.url) match {
        case x: SearchResponse if x.getHits.getTotalHits > 0 => true
        case _ => false
      }
    } catch {
      case e: IndexMissingException => false
      case e: Exception => false
    }
  }
}
