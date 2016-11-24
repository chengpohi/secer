package com.github.chengpohi.indexer

import akka.actor.{Actor, ActorLogging}
import com.github.chengpohi.api.ElasticDSL
import com.github.chengpohi.model.IndexItem
import com.github.chengpohi.registry.ELKCommandRegistry

import scala.util.{Failure, Success}

/**
  * Created by xiachen on 1/17/15.
  */
class PageIndexerService extends Actor with ActorLogging{
  val dsl = new ElasticDSL(ELKCommandRegistry.client)

  import dsl._
  import context.dispatcher


  def receive: Receive = {
    case page: IndexItem => {
      log.info("index page: {}", page.fetchItem.id)
      val result = DSL {
        index into page.fetchItem.indexName / page.fetchItem.indexType doc page.map
      }
      result onComplete {
        case Success(r) => log.info(s"index success ${page.fetchItem.id}")
        case Failure(f) => log.info(s"index fail ${page.fetchItem.id}, cause: ${f.getMessage}")
      }
    }
  }
}
