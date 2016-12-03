package com.github.chengpohi.config

import com.github.chengpohi.model.Feed
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._
import scala.io.Source

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
object AppStoreConfig {
  private[this] val loadConfig = ConfigFactory.load("appstore.conf")
  private[this] val appstore: Config = loadConfig.getConfig("appstore")
  val INTERVAL: Int = appstore.getInt("interval")
  val INITIAL_DELAY: Int = appstore.getInt("initialDelay")
  val SELECTORS: String = Source.fromInputStream(this.getClass.getResourceAsStream("/selectors.txt")).mkString
  val baseUrl: String = appstore.getString("urlBase")
  val countries = appstore.getStringList("countries").asScala.toList
  val feedTypes = appstore.getStringList("feedTypes").asScala.toList
  val limit: Int = appstore.getInt("limit")
  val tpe: String = appstore.getString("type")
  val genres = appstore.getStringList("genre").asScala.toList


  def feeds: List[Feed] = for {
    country <- countries
    feedType <- feedTypes
    genre <- genres
  } yield Feed(country, feedType, genre, baseUrl.format(country, feedType, limit, genre, tpe))
}
