/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi.scheduler

import akka.actor.ActorRef
import com.github.chengpohi.api.ElasticDSL
import com.github.chengpohi.config.AppStoreConfig
import com.github.chengpohi.config.AppStoreConfig.SELECTORS
import com.github.chengpohi.connector.ElasticClientConnector
import com.github.chengpohi.model.{Feed, FetchItem}
import org.apache.logging.log4j.{LogManager, Logger}
import org.json4s._
import org.json4s.native.JsonMethods._
import org.jsoup.Jsoup

/**
  * secer
  * Created by chengpohi on 10/13/16.
  */
class FeedSelector(crawler: ActorRef) extends Runnable {
  private val logger: Logger = LogManager.getLogger(this.getClass)
  implicit val formats = org.json4s.DefaultFormats
  val dsl = new ElasticDSL(ElasticClientConnector.client)
  import dsl._

  override def run(): Unit = {
    val searchResponse = DSL {
      search in "appstore" / AppStoreConfig.genres.head size 10 scroll "1m"
    }.await
    val fetchItems: List[FetchItem] = List()
    fetchItems.foreach(item => {
      crawler ! item
    })
    logger.info("send {} select items finished", fetchItems.size)
  }
}
