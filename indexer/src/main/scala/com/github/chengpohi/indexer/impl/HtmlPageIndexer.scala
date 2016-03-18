package com.github.chengpohi.indexer.impl

import java.util.concurrent.Executors
import com.github.chengpohi.indexer.config.IndexerConfig
import com.github.chengpohi.model.Page
import org.slf4j.LoggerFactory

import scala.concurrent._

/**
 * seccrawler
 * Created by chengpohi on 8/24/15.
 */
class HtmlPageIndexer {
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(IndexerConfig.MAX_THREADS))

  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  def asyncIndex(page: Page): Future[String] = {
    Future {
      blocking {
        LOG.info("Index Url: " + page.fetchItem.url.toString)
        ""
      }
    }
  }

}
