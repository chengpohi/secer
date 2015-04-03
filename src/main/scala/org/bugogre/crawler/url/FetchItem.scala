package org.bugogre.crawler.url

import org.bugogre.crawler.indexer.FieldSelector
import org.bugogre.crawler.rule.Rule
import org.bugogre.crawler.url.filter.UrlFilter

/**
 * Created by xiachen on 12/13/14.
 */
case class FetchItem(url: String, indexName: String, indexType: String, selectors: List[FieldSelector]) {
  def filter(rule: Rule): Boolean = {
    UrlFilter.filterByRule(url, rule)
    UrlFilter.filterByTime(url, rule)
  }
}
