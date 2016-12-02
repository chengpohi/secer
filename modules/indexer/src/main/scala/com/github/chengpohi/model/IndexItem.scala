package com.github.chengpohi.model

import org.jsoup.nodes.Document

/**
  * IndexPage
  * Created by xiachen on 12/16/14.
  */
case class IndexItem(document: Document, fetchItem: FetchItem, md5: String, indexes: Map[String, Any]) {
  def map: Map[String, Any] = {
    indexes + ("uuid" -> fetchItem.id) + ("md5" -> md5) + ("url" -> fetchItem.url) + ("created_at" -> System.nanoTime())
  }
}
