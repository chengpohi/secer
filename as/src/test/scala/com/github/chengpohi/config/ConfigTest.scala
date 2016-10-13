/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi.config

import org.scalatest._

/**
  * secer
  * Created by chengpohi on 10/13/16.
  */
class ConfigTest extends FlatSpec with Matchers {
  "Config getSeed" should "generate all seeds" in {
    val seeds: List[String] = Config.getSeeds
    seeds.size should be (9)
    seeds should contain ("https://itunes.apple.com/us/rss/newapplications/limit=100/genre=6014/json")
  }
}
