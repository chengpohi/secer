package org.bugogre.crawler.html

import org.bugogre.crawler.indexer.IndexField
import org.bugogre.crawler.url.FetchItem
import org.jsoup.nodes.Document

/**
 * Created by xiachen on 12/16/14.
 */
case class Page(doc: Document, fetchItem: FetchItem, md5: String, urlMd5: String, indexes: List[IndexField]) {}
