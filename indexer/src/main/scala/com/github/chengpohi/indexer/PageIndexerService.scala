package com.github.chengpohi.indexer

import akka.actor.Actor
import com.github.chengpohi.api.ElasticDSL
import com.github.chengpohi.model.IndexItem
import com.github.chengpohi.registry.ELKCommandRegistry
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success}

/**
  * Created by xiachen on 1/17/15.
  */
class PageIndexerService extends Actor {
  lazy val log = LoggerFactory.getLogger(getClass.getName)
  val dsl = new ElasticDSL(ELKCommandRegistry.client)

  import dsl._
  import DSLHelper._
  import context.dispatcher


  def receive: Receive = {
    case page: IndexItem => {
      log.info("index page: {}", page.fetchItem.id)
      val result = DSL {
        index into page.fetchItem.indexName / page.fetchItem.indexType doc page.map
      }
      result onComplete {
        case Success(r) => log.info(s"index success ${page.fetchItem.id}")
        case Failure(f) => log.warn(s"index fail ${page.fetchItem.id}, cause: ${f.getMessage}")
      }
    }
  }
}
