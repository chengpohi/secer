package com.github.chengpohi.app.config

import com.typesafe.config.ConfigFactory

object CrawlerConfig {
  lazy val CRAWLER_CONFIG = ConfigFactory.load("crawler")
}
