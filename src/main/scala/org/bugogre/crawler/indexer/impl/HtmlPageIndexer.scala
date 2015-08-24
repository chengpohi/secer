package org.bugogre.crawler.indexer.impl

import java.util.concurrent.Executors

import com.secer.elastic.model.Page
import org.slf4j.LoggerFactory

import scala.concurrent._
import org.bugogre.crawler.config.SecConfig

import com.secer.elastic.controller.PageController

/**
 * seccrawler
 * Created by chengpohi on 8/24/15.
 */
class HtmlPageIndexer {
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.MAX_THREADS))

  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  def asyncIndex(page: Page): Future[String] = {
    Future {
      blocking {
        LOG.info("Index Url: " + page.fetchItem.url.toString)
        PageController.indexPage(page)
        ""
      }
    }
  }

}
