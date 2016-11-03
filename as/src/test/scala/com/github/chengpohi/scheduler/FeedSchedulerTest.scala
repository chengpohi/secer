package com.github.chengpohi.scheduler

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by xiachen on 03/11/2016.
  */
class FeedSchedulerTest extends FlatSpec with Matchers {
  it should "shoud match the itunes href regex" in {
    val result = "https://itunes.apple.com/us/app/fashion-styles-beauty-shop/id1149649020?mt=8&ign-mpt=uo%3D2"
      .matches("^https:\\/\\/itunes\\.apple\\.com\\/%s\\/app\\/.*".format("us"))
    assert(result === true)
  }
}
