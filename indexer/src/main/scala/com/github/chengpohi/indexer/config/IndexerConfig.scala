package com.github.chengpohi.indexer.config

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._
import scala.util.matching.Regex

object IndexerConfig {
  lazy val CRAWLER_CONFIG = ConfigFactory.load("indexer")
  lazy val INDEXER_THREADS = CRAWLER_CONFIG.getConfig("threads").getInt("indexer")
}
