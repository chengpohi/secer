package com.github.chengpohi.indexer

import akka.actor.{Actor, ActorLogging}
import com.github.chengpohi.api.ElasticDSL
import com.github.chengpohi.model.IndexTrait
import com.github.chengpohi.registry.ELKCommandRegistry

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by xiachen on 1/17/15.
  */
trait IndexStatus

case object Finished extends IndexStatus

class PageIndexerService extends Actor with ActorLogging {
  val dsl = new ElasticDSL(ELKCommandRegistry.client)

  import dsl._


  def receive: Receive = {
    case page: IndexTrait =>
      log.info("index item: {}", page.id)
      val result = DSL {
        index into page.indexName / page.indexType doc page.doc id page.id
      }
      val s = sender()
      result.onComplete {
        case Success(res) =>
          log.info(s"index success :  ${res.getId}")
          s ! Finished
        case Failure(e) =>
          log.error(s"index fail id ${page.id}, cause: ${e}")
          s ! Finished
      }
  }
}
