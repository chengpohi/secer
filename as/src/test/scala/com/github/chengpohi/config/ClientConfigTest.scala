/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi.config

import com.github.chengpohi.model.Feed
import org.scalatest._

/**
  * secer
  * Created by chengpohi on 10/13/16.
  */
class ClientConfigTest extends FlatSpec with Matchers {
  "Config getSeed" should "generate all seeds" in {
    val seeds: List[Feed] = AppStoreConfig.feeds
    seeds.size should be (9)
    seeds should contain (Feed("us", "newapplications", "6014", "https://itunes.apple.com/us/rss/newapplications/limit=100/genre=6014/json"))
  }
}
