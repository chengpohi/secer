package org.bugogre.crawler.url

import org.bugogre.crawler.filter.UrlFilter
import org.bugogre.crawler.indexer.FieldSelector
import org.bugogre.crawler.rule.Rule

/**
 * Created by xiachen on 12/13/14.
 */
case class FetchItem(url: String, indexName: String, indexType: String, selectors: List[FieldSelector]) {
  def filter(rule: Rule): Boolean = {
    UrlFilter.filterByRule(url, rule)
  }
}
