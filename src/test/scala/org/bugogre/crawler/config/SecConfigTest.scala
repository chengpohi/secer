package org.bugogre.crawler.config

import org.scalatest.FlatSpec

/**
 * Created by xiachen on 12/16/14.
 */
class SecConfigTest extends FlatSpec{
  "Thread" should " fetch successfully" in {
    assert(SecConfig.threads.getInt("fetcher") === 10)
  }

  "Exclude Urls" should " fetch successfully" in {
    assert(SecConfig.excludeUrlPatterns.size >= 2)
  }
}
