package com.github.chengpohi.impl

import com.github.chengpohi.api.ElasticDSL
import com.github.chengpohi.model.FetchItem
import com.github.chengpohi.registry.ELKCommandRegistry
import org.elasticsearch.action.get.GetResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


/**
  * Page Fetcher
  * Created by xiachen on 3/1/15.
  */
class HtmlPageFetcher {
  val dsl = new ElasticDSL(ELKCommandRegistry.client)

  import dsl._

  def fetch(fetchItem: FetchItem): Option[(FetchItem, Document)] = {
    val result: (FetchItem, Document, Int) = connect(fetchItem)
    result._3 match {
      case 200 => Some((result._1, result._2))
      case _ => None
    }
  }

  def filter(item: FetchItem): Boolean = {
    val res: GetResponse = DSL {
      search in item.indexName / item.indexType where id equal item.id.toString
    }.await
    res.isExists
  }

  def connect(item: FetchItem): (FetchItem, Document, Int) = {
    Thread.sleep(3000)
    val doc = Jsoup.connect(item.url).timeout(50000).execute()
    (item, doc.parse(), doc.statusCode())
  }
}
