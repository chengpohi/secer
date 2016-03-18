package com.github.chengpohi.model

import java.net.URL

/**
 * Fetch Item
 * Created by chengpohi on 3/18/16.
 */
case class FetchItem(url: URL, indexName: String, indexType: String, selectors: List[FieldSelector], urlRegex: Option[String] = None) {
  def filterByRule(): Boolean = {
    false
  }

  def filterOrFetch(fetch: (FetchItem) => Unit) = {
    filterByRule() match {
      case false => fetch(this)
      case _ =>
    }
  }
}
