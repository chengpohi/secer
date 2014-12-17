package org.bugogre.crawler.html

import org.bugogre.crawler.url.Url
import org.jsoup.nodes.Document

/**
 * Created by xiachen on 12/16/14.
 */
case class Html(title: String, doc: Document, url: Url)
