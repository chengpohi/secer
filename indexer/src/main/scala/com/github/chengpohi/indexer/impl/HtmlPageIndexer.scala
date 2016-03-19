package com.github.chengpohi.indexer.impl

import java.util.concurrent.Executors
import com.github.chengpohi.api.ElasticCommand
import com.github.chengpohi.indexer.config.IndexerConfig
import com.github.chengpohi.model.IndexPage
import org.slf4j.LoggerFactory

import scala.concurrent._

/**
 * seccrawler
 * Created by chengpohi on 8/24/15.
 */
class HtmlPageIndexer {
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(IndexerConfig.INDEXER_THREADS))
  implicit val client = IndexerConfig.client

  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  def asyncIndex(page: IndexPage): Future[String] = {
    Future {
      blocking {
        LOG.info("Index Url: " + page.fetchItem.url)
        ElasticCommand.indexMap(page.fetchItem.indexName, page.fetchItem.indexType, page)
      }
    }
  }
}
