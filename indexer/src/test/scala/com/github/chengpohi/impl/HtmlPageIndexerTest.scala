package com.github.chengpohi.impl

import com.github.chengpohi.api.ElasticCommand
import com.github.chengpohi.builder.IndexPageBuilder
import com.github.chengpohi.indexer.config.IndexerConfig
import com.github.chengpohi.indexer.impl.HtmlPageIndexer
import org.scalatest.{BeforeAndAfter, FlatSpec}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * secer
 * Created by chengpohi on 3/19/16.
 */
class HtmlPageIndexerTest extends FlatSpec with BeforeAndAfter{
  implicit val client = IndexerConfig.client
  val htmlPageIndexer: HtmlPageIndexer = new HtmlPageIndexer

  before {
  }

  "HtmlPageIndexer" should "index indexPage to elk" in {
    val indexPage = IndexPageBuilder.indexPage
    val result: Future[String] = htmlPageIndexer.asyncIndex(indexPage)
    val id: String = Await.result(result, Duration.Inf)
    assert(id != null)
  }

  after {
    ElasticCommand.deleteIndex("test-index-name")
  }
}
