package org.bugogre.crawler.fetcher.impl

import com.secer.elastic.model.FetchItem
import org.bugogre.crawler.httpclient.{HttpResponse, Web}

/**
 * Created by xiachen on 3/1/15.
 */
object HtmlPageFetcher {
  def fetch(item: FetchItem): Web = {
    HttpResponse ==> item
  }
}
