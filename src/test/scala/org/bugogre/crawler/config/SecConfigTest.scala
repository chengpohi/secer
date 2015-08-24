package org.bugogre.crawler.config

import org.scalatest.FlatSpec

/**
 * Created by xiachen on 12/16/14.
 */
class SecConfigTest extends FlatSpec{
  "Thread" should " fetch successfully" in {
    assert(SecConfig.MAX_THREADS === 5)
  }

  "Exclude Urls" should " fetch successfully" in {
    assert(SecConfig.EXCLUDE_URL_PATTERNS.size >= 1)
  }
}
