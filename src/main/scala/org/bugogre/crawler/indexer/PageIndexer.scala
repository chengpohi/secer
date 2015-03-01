package org.bugogre.crawler.indexer

import akka.actor.Actor
import org.bugogre.crawler.html.Page
import org.bugogre.crawler.indexer.impl.ElasticIndexer
import org.slf4j.LoggerFactory

/**
 * Created by xiachen on 1/17/15.
 */
class PageIndexer extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  def receive: Receive = {
    case str: String => {
      str match {
        case "indexUrl" => sender() ! ElasticIndexer.indexConfig.getString("host")
      }
    }
    case page: Page => {
      LOG.info("Index Url: " + page.fetchItem.url)
      ElasticIndexer.index4elasticsearch(page)
      sender() ! "page index"
    }
  }
}
