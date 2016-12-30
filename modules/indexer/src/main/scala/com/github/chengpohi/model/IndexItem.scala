package com.github.chengpohi.model

import org.jsoup.nodes.Document

/**
  * IndexPage
  * Created by xiachen on 12/16/14.
  */
trait IndexTrait {
  def doc: Map[String, Any]

  def indexName: String

  def indexType: String

  def id: String
}

case class IndexItem(document: Document, fetchItem: FetchItem, md5: String, indexes: Map[String, Any]) extends IndexTrait {
  override def doc: Map[String, Any] = {
    indexes + ("uuid" -> fetchItem.id) + ("md5" -> md5) + ("url" -> fetchItem.url) + ("created_at" -> System.nanoTime())
  }

  override def indexName: String = fetchItem.indexName

  override def indexType: String = fetchItem.indexType

  override def id: String = fetchItem.id.toString
}
