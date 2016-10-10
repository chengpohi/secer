package com.github.chengpohi.model

import org.jsoup.nodes.Document

/**
  * IndexPage
  * Created by xiachen on 12/16/14.
  */
case class IndexPage(document: Document, fetchItem: FetchItem, md5: String, urlMd5: String, indexes: List[IndexField]) {
  def doc: Map[String, Any] = indexes.groupBy(k => k.field).map(k => (k._2.head.field, k._2.head.content)) ++ Map(
    "md5" -> md5,
    "url" -> fetchItem.url,
    "urlMd5" -> urlMd5,
    "created_at" -> System.nanoTime()
  )
}
