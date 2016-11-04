package com.github.chengpohi.app.config

import com.typesafe.config.ConfigFactory

object CrawlerConfig {
  lazy val CRAWLER_CONFIG = ConfigFactory.load("crawler")
  lazy val CRAWLER_HTTP = ConfigFactory.load("http").getConfig("http")
  lazy val CRAWLER_HTTP_PORT = ConfigFactory.load("http").getConfig("http").getInt("port")
  lazy val CRAWLER_HTTP_IP = ConfigFactory.load("http").getConfig("http").getString("ip")
}
