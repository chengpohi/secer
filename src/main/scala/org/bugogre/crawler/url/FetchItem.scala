package org.bugogre.crawler.url

import org.bugogre.crawler.filter.UrlFilter
import org.bugogre.crawler.indexer.FieldSelector

/**
 * Created by xiachen on 12/13/14.
 */
case class FetchItem(url: String, indexName: String, indexType: String, selectors: List[FieldSelector]) {
  def filterByRule(): Boolean = {
    UrlFilter.filterByRule(url)
  }

  def filterOrFetch(fetch:(FetchItem) => Unit) = {
    filterByRule() match {
      case false => fetch(this)
      case _ =>
    }
  }
}
