package org.bugogre.crawler.fetcher.impl

import java.net.{NoRouteToHostException, SocketException, UnknownHostException}
import java.util.concurrent.Executors

import akka.actor.ActorRef
import com.secer.elastic.model.FetchItem
import com.secer.elastic.controller.PageController._
import com.secer.elastic.util.HashUtil
import org.apache.http.NoHttpResponseException
import org.apache.http.client.ClientProtocolException
import org.apache.http.conn.HttpHostConnectException
import org.bugogre.crawler.cache.URLCache.FETCH_ITEM_CACHE
import org.bugogre.crawler.config._
import org.bugogre.crawler.httpclient.HttpResponse
import org.slf4j.{LoggerFactory, MDC}

import scala.concurrent._


/**
 * Page Fetcher
 * Created by xiachen on 3/1/15.
 */
class HtmlPageFetcher(pageParser: ActorRef) {
  lazy val LOG = LoggerFactory.getLogger(getClass.getName)

  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(SecConfig.MAX_THREADS))

  def fetch(fetchItem: FetchItem): String = {
    try {
      MDC.put("logFileName", (Thread.currentThread().getId % SecConfig.MAX_THREADS + 1).toString)
      LOG.info("Cache Size: " + FETCH_ITEM_CACHE.size + ", Fetch Url: " + fetchItem.url.toString)
      val w = HttpResponse ==> fetchItem
      pageParser ! w
      MDC.remove("logFileName")
      fetchItem.url.toString + " fetch finished."
    } catch {
      case e: ClientProtocolException => "client redirect exception:" + fetchItem.url.toString
      case e: UnknownHostException => "unknown url: " + fetchItem.url.toString
      case e: HttpHostConnectException => "host can't connect exception:" + fetchItem.url.toString
      case e: NoRouteToHostException => "no route to host exception:" + fetchItem.url.toString
      case e: SocketException => "socket exception:" + fetchItem.url.toString
      case e: NoHttpResponseException => "no http response exception: " + fetchItem.url.toString
    }
  }

  def filterFetchedItem(item: FetchItem): Boolean = {
    this.synchronized {
      val hashUrl = HashUtil.hashString(item.url.toString)
      if (FETCH_ITEM_CACHE.containsKey(hashUrl)) {
        return false
      }
      if (pageWhetherExist(item)) {
        FETCH_ITEM_CACHE.put(hashUrl, item)
        return false
      }
      FETCH_ITEM_CACHE.put(hashUrl, item)
      true
    }
  }

  def filterFetchItemByUrlRegex(url: String, regex: String): Boolean = {
    url.matches(regex)
  }

  def filter(item: FetchItem): Boolean = {
    filterFetchItemByUrlRegex(item.url.toString, item.urlRegex.get) && filterFetchedItem(item)
  }


  def asyncFetch(fetchItem: FetchItem): Future[String] = {
    Future {
      blocking {
        fetch(fetchItem)
      }
    }
  }
}
