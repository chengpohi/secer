package org.bugogre.crawler.indexer

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.typesafe.config.ConfigFactory
import org.bugogre.crawler.html.Page

/**
 * Created by xiachen on 1/17/15.
 */
class HtmlIndexer extends Actor{
  lazy val indexConfig = ConfigFactory.load("indexer.conf")
  lazy val indexUrl = indexConfig.getConfig("index").getString("url")
  def index(page: Page) = {
  }

  def receive: Receive = {
    case str: String => {
      str match {
        case "indexUrl" => sender ! indexUrl
      }
    }
    case page: Page => index(page)
  }
}
