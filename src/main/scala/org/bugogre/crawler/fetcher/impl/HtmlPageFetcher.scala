package org.bugogre.crawler.fetcher.impl

import org.bugogre.crawler.httpclient.{WebFactory, Web}
import org.bugogre.crawler.url.FetchItem

/**
 * Created by xiachen on 3/1/15.
 */
object HtmlPageFetcher {
  def fetch(item: FetchItem): Web = {
    WebFactory ==> item
  }
}
