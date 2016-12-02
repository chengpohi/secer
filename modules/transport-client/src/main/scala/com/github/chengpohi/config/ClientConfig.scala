package com.github.chengpohi.config

import com.typesafe.config.ConfigFactory

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
object ClientConfig {
  private[this] val CRAWLER_CONFIG = ConfigFactory.load("crawler.conf").getConfig("crawler")
  val CRAWLER_HOST = CRAWLER_CONFIG.getString("host")
}
