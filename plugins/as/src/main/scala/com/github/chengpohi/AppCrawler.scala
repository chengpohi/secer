/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi

import java.util.concurrent.TimeUnit

import com.github.chengpohi.config.AppStoreConfig
import com.github.chengpohi.scheduler.FeedScheduler

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

/**
  * secer
  * Created by chengpohi on 10/13/16.
  */
object AppCrawler {
  def main(args: Array[String]): Unit = {
    val actorSystem = TransportClient.actorSystem
    val feeds = AppStoreConfig.feeds
    actorSystem.scheduler.schedule(
      initialDelay = Duration(AppStoreConfig.INITIAL_DELAY, TimeUnit.SECONDS),
      interval = Duration(AppStoreConfig.INTERVAL, TimeUnit.SECONDS),
      runnable = new FeedScheduler(feeds, TransportClient.crawler))
  }
}
