package org.bugogre.crawler.indexer

import akka.actor.Actor
import com.secer.elastic.controller.PageController
import com.secer.elastic.model.Page
import org.slf4j.LoggerFactory



/**
 * Created by xiachen on 1/17/15.
 */
class PageIndexer extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  def receive: Receive = {
    case page: Page => {
      LOG.info("Index Url: " + page.fetchItem.url)
      PageController.indexPage(page)
      sender() ! "page index"
    }
  }
}
