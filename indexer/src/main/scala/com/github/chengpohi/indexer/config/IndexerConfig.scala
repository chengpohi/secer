package com.github.chengpohi.indexer.config

import com.github.chengpohi.connector.ElasticClientConnector
import com.typesafe.config.ConfigFactory

object IndexerConfig {
  lazy val INDEXER_CONFIG = ConfigFactory.load("indexer")
  lazy val INDEXER_THREADS = INDEXER_CONFIG.getConfig("threads").getInt("indexer")
  lazy val host = INDEXER_CONFIG.getConfig("index").getString("host")
  lazy val port = INDEXER_CONFIG.getConfig("index").getInt("port")
  val client = ElasticClientConnector.buildClient(host, port)
}
