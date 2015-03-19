package org.bugogre.crawler.config

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions._
import scala.util.matching.Regex

object SecConfig {
  lazy val CRAWLER_CONFIG = ConfigFactory.load()
  lazy val MAX_THREADS = CRAWLER_CONFIG.getConfig("threads").getInt("fetcher")
  lazy val EXCLUDE_URL_PATTERNS: List[Regex] = CRAWLER_CONFIG.getStringList("excludeUrls").toList.flatMap(u => List(u.r))
  lazy val INDEX_URL = CRAWLER_CONFIG.getConfig("index").getConfig("host")
  lazy val CRAWLER_PORT = CRAWLER_CONFIG.getConfig("crawler").getString("host")
}
