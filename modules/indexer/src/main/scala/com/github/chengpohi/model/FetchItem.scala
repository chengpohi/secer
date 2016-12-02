package com.github.chengpohi.model

/**
  * Fetch Item
  * Created by chengpohi on 3/18/16.
  */
case class FetchItem(url: String, indexName: String, indexType: String,
                     selectors: String, urlRegex: Option[String] = Some(".*"),
                     delay: Option[Int] = Some(0), bfs: Option[Boolean] = Some(true)) {
  val id: BigInt = url.toCharArray.foldLeft(BigInt(0))((a, b) => a * 131 + b.toInt) % 100000000
}
