package com.github.chengpohi.indexer

import akka.actor.Actor
import com.github.chengpohi.model.IndexPage
import org.slf4j.LoggerFactory


/**
 * Created by xiachen on 1/17/15.
 */
class PageIndexerService extends Actor {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)
  val htmlPageIndexer = new impl.HtmlPageIndexer

  def receive: Receive = {
    case page: IndexPage => htmlPageIndexer.asyncIndex(page)
  }
}
