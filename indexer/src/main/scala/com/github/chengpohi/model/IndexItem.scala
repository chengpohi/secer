package com.github.chengpohi.model

import org.jsoup.nodes.Document

/**
  * IndexPage
  * Created by xiachen on 12/16/14.
  */
case class IndexItem(document: Document, fetchItem: FetchItem, md5: String, urlMd5: String, indexes: Map[String, Any]) {
  def doc: Map[String, Any] =
    indexes + ("md5" -> md5) + ("url" -> fetchItem.url) + ("urlMd5" -> urlMd5) + ("created_at" -> System.nanoTime())
}
