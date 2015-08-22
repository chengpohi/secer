package org.bugogre.crawler.indexer

import java.util.concurrent.Executors

import akka.actor.Actor
import com.secer.elastic.controller.PageController
import com.secer.elastic.model.Page
import org.bugogre.crawler.config.SecConfig
import org.slf4j.LoggerFactory

import scala.concurrent._


/**
 * Created by xiachen on 1/17/15.
 */
class PageIndexer extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.MAX_THREADS))

  def asyncIndex(page: Page): Future[String] = {
    Future {
      blocking {
        LOG.info("Index Url: " + page.fetchItem.url.toString)
        PageController.indexPage(page)
        ""
      }
    }
  }

  def receive: Receive = {
    case page: Page => asyncIndex(page)
  }
}
