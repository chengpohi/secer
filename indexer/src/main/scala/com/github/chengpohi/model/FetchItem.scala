package com.github.chengpohi.model

/**
  * Fetch Item
  * Created by chengpohi on 3/18/16.
  */
case class FetchItem(url: String, indexName: String, indexType: String, selectors: String, urlRegex: Option[String] = Some(".*"))
