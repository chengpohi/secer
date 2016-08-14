package com.github.chengpohi.config

import java.util.Base64

import com.github.chengpohi.model.User
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._

/**
  * fc
  * Created by chengpohi on 8/14/16.
  */
object Config {
  private[this] val loadConfig: Config = ConfigFactory.load("users.conf")
  lazy val users = loadConfig.getConfig("crawler").getObjectList("users").asScala
    .map(c => User(c.toConfig.getString("mail"), decode(c.toConfig.getString("pass"))))
  def decode(s: String): String = new String(Base64.getDecoder.decode(s))
}
