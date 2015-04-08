package org.bugogre.crawler.builder

import org.bugogre.crawler.httpclient.HttpResponse

/**
 * Created by xiachen on 3/1/15.
 */
object WebBuilder {
  def web = {
    HttpResponse ==> FetchItemBuilder.fetchItem
  }
}
