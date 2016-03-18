package com.github.chengpohi.app.config

import com.typesafe.config.ConfigFactory

object CrawlerConfig {
  lazy val CRAWLER_CONFIG = ConfigFactory.load("crawler")
  lazy val CRAWLER_PORT = CRAWLER_CONFIG.getConfig("crawler").getString("host")
}
