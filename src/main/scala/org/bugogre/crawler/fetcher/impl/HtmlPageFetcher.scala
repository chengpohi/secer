package org.bugogre.crawler.fetcher.impl

import org.bugogre.crawler.httpclient.{HttpResponse, Web}
import org.bugogre.crawler.url.FetchItem

/**
 * Created by xiachen on 3/1/15.
 */
object HtmlPageFetcher {
  def fetch(item: FetchItem): Web = {
    HttpResponse ==> item
  }
}
