/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi.scheduler

import com.github.chengpohi.model.Feed
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

/**
  * secer
  * Created by chengpohi on 10/13/16.
  */
class FeedScheduler(feeds: List[Feed]) extends Runnable {
  lazy val LOGGER = LoggerFactory.getLogger(getClass.getName)

  override def run(): Unit = {
    val documents: List[String] = feeds.map(f => {
      LOGGER.info("get feed type: {}, genre: {}, url: {}", f.feedType, f.genre, f.url)
      Jsoup.connect(f.url).execute().body()
    })
    documents.foreach(println)
  }
}
