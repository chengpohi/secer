package com.github.chengpohi.indexer

import akka.actor.Actor
import com.github.chengpohi.api.ElasticDSL
import com.github.chengpohi.model.IndexItem
import com.github.chengpohi.registry.ELKCommandRegistry
import org.slf4j.LoggerFactory

/**
  * Created by xiachen on 1/17/15.
  */
class PageIndexerService extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  val dsl = new ElasticDSL(ELKCommandRegistry.client)

  import dsl._
  import DSLHelper._

  def receive: Receive = {
    case page: IndexItem => {
      LOG.info("index page: {}", page.fetchItem.url)
      DSL {
        index into page.fetchItem.indexName / page.fetchItem.indexType doc page.map
      }
    }
  }
}
