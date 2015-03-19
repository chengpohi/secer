package org.bugogre.crawler.builder

import org.bugogre.crawler.httpclient.WebFactory

/**
 * Created by xiachen on 3/1/15.
 */
object WebBuilder {
  def web = {
    WebFactory ==> FetchItemBuilder.fetchItem
  }
}
