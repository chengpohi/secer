package org.bugogre.crawler.indexer


import akka.actor.Actor
import com.secer.elastic.model.Page
import org.bugogre.crawler.indexer.impl.HtmlPageIndexer
import org.slf4j.LoggerFactory



/**
 * Created by xiachen on 1/17/15.
 */
class PageIndexerService extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  val htmlPageIndexer = new HtmlPageIndexer

  def receive: Receive = {
    case page: Page => htmlPageIndexer.asyncIndex(page)
  }
}
