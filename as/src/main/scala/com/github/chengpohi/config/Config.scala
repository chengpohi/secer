package com.github.chengpohi.config

import java.util.Base64

import com.github.chengpohi.model.AppSeed
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
object Config {
  private[this] val loadConfig = ConfigFactory.load("appstore.conf")
  def decode(s: String): String = new String(Base64.getDecoder.decode(s))

  def getSeeds: List[AppSeed] = {
    val appstore: Config = loadConfig.getConfig("appstore")
    val urlBase: String = appstore.getString("urlBase")
    val countries = appstore.getStringList("countries").asScala.toList
    val feedTypes = appstore.getStringList("feedTypes").asScala.toList
    val limit: Int = appstore.getInt("limit")
    val tpe: String = appstore.getString("type")
    val genres = appstore.getStringList("genre").asScala.toList

    for {
      country <- countries
      feedType <- feedTypes
      genre <- genres
    } yield AppSeed(country, feedType, genre, urlBase.format(country, feedType, limit, genre, tpe))
  }
}
