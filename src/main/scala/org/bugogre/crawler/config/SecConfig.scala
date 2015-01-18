package org.bugogre.crawler.config

import com.typesafe.config.ConfigFactory
import scala.collection.JavaConversions._
import scala.util.matching.Regex

object SecConfig {
  lazy val config = ConfigFactory.load()
  lazy val threads = config.getConfig("threads")
  lazy val excludeUrlPatterns: List[Regex] = config.getStringList("excludeUrls").toList.flatMap(u => List(u.r))
  lazy val indexUrl = config.getConfig("index").getConfig("host")
}
