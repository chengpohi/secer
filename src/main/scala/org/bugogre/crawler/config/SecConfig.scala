package org.bugogre.crawler.config

import com.typesafe.config.ConfigFactory 

object SecConfig {
  lazy val config = ConfigFactory.load()
  lazy val threads = config.getConfig("threads")
}
