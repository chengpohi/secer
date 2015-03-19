package org.bugogre.crawler.elastic

import com.sksamuel.elastic4s.ElasticClient
import com.typesafe.config.ConfigFactory
import org.elasticsearch.common.settings.ImmutableSettings

/**
 * Created by xiachen on 3/19/15.
 */
object ElasticClientConnector {
  lazy val indexConfig = ConfigFactory.load("indexer.conf").getConfig("index")
  lazy val settings = ImmutableSettings.settingsBuilder().put("cluster.name", indexConfig.getString("cluster.name")).build()
  lazy val client = ElasticClient.remote(settings, (indexConfig.getString("host"), indexConfig.getInt("port")))
}
