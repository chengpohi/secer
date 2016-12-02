/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi.scheduler

import akka.actor.ActorRef
import com.github.chengpohi.config.AppStoreConfig.SELECTORS
import com.github.chengpohi.model.{Feed, FetchItem}
import org.apache.logging.log4j.{LogManager, Logger}
import org.json4s._
import org.json4s.native.JsonMethods._
import org.jsoup.Jsoup

/**
  * secer
  * Created by chengpohi on 10/13/16.
  */
class FeedScheduler(feeds: List[Feed], crawler: ActorRef) extends Runnable {
  private val logger: Logger = LogManager.getLogger(this.getClass)
  implicit val formats = org.json4s.DefaultFormats

  override def run(): Unit = {
    val fetchItems: List[FetchItem] = feeds.flatMap(f => {
      logger.info(s"get feed type: ${f.feedType}, genre: ${f.genre}, url: ${f.url}")
      val body: String = Jsoup.connect(f.url).timeout(1000 * 20).execute().body()
      for {
        r <- (parse(body) \ "feed" \\ "link" \\ "href").children.map(i => i.extract[String])
      } yield FetchItem(r,
        "appstore",
        f.genre,
        SELECTORS,
        Some("^https://itunes.apple.com/%s/app/.*".format(f.country)),
        delay = Some(2000),
        bfs = Some(false)
      )
    })
    fetchItems.foreach(item => {
      crawler ! item
    })
    logger.info("send {} items finished", fetchItems.size)
  }
}
