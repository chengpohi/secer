package org.bugogre.crawler.html

import org.bugogre.crawler.url.Url
import org.jsoup.nodes.Document

/**
 * Created by xiachen on 12/16/14.
 */
case class Page(title: String, doc: Document, url: Url, indexName: Option[String] = None, indexType: Option[String] = None, tag: Option[String] = None) {
}
