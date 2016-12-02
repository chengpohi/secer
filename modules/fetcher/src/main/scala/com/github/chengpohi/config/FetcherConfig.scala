package com.github.chengpohi.config

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._
import scala.util.matching.Regex

object FetcherConfig {
  lazy val CRAWLER_CONFIG = ConfigFactory.load("fetcher")
  lazy val MAX_THREADS = CRAWLER_CONFIG.getConfig("threads").getInt("fetcher")
  lazy val INDEX_URL = CRAWLER_CONFIG.getConfig("index").getConfig("host")
}
