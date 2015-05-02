package org.bugogre.crawler.elastic.index

import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.source.StringDocumentSource
import org.bugogre.crawler.elastic.ElasticClientConnector
import org.bugogre.crawler.html.Page
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.parsing.json.JSONObject
import scala.util.{Failure, Success}

/**
 * Created by xiachen on 3/1/15.
 */
object ElasticIndexer {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  lazy val client = ElasticClientConnector.client

  def index4elasticsearch(page: Page): Unit = {
    var indexes = page.indexes.groupBy(k => k.field).map(k => (k._2(0).field, k._2(0).content))
    indexes += ("_md5" -> page.md5)
    indexes += ("_urlMd5" -> page.urlMd5)
    indexes += ("_url" -> page.fetchItem.url)
    indexes += ("_date" -> System.currentTimeMillis().toString)

    val state = client execute {
      index into page.fetchItem.indexName -> page.fetchItem.indexType doc StringDocumentSource(JSONObject(indexes).toString())
    }

    state onComplete {
      case Success(t) => LOG.info("Index Url: " + page.fetchItem.url + " Success")
      case Failure(t) => LOG.error("A Index Error Occurrence: " + t.getMessage)
    }
  }
}


