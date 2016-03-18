package com.github.chengpohi.parser.config

import com.typesafe.config.ConfigFactory

/**
 * secer
 * Created by chengpohi on 3/18/16.
 */
object ParserConfig {
  lazy val PARSER_CONFIG = ConfigFactory.load("parser")
  lazy val PARSER_POOL = PARSER_CONFIG.getConfig("threads").getInt("parser")
}
