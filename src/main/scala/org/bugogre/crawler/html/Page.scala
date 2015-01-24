package org.bugogre.crawler.html

import org.bugogre.crawler.url.FetchItem
import org.jsoup.nodes.Document

/**
 * Created by xiachen on 12/16/14.
 */
case class Page(title: String, doc: Document, url: FetchItem, indexName: Option[String] = None, indexType: Option[String] = None, tag: Option[String] = None) {
}
