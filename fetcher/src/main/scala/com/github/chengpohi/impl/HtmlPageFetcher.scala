package com.github.chengpohi.impl

import java.net.{NoRouteToHostException, SocketException, UnknownHostException}
import java.util.concurrent.Executors

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.github.chengpohi.cache.URLCache.FETCH_ITEM_CACHE
import com.github.chengpohi.config.FetcherConfig
import com.github.chengpohi.httpclient.HttpResponse
import com.github.chengpohi.model.FetchItem
import com.github.chengpohi.util.Utils
import org.apache.http.NoHttpResponseException
import org.apache.http.client.ClientProtocolException
import org.apache.http.conn.HttpHostConnectException
import org.slf4j.MDC

import scala.concurrent._


/**
 * Page Fetcher
 * Created by xiachen on 3/1/15.
 */
class HtmlPageFetcher(pageParser: ActorRef, fetchItem: FetchItem) extends Actor with ActorLogging {
  implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(FetcherConfig.MAX_THREADS))

  def fetch(fetchItem: FetchItem): String = {
    try {
      MDC.put("logFileName", (Thread.currentThread().getId % FetcherConfig.MAX_THREADS + 1).toString)
      log.info("Cache Size: " + FETCH_ITEM_CACHE.size + ", Fetch Url: " + fetchItem.url)
      val w = HttpResponse ==> fetchItem
      pageParser ! w
      MDC.remove("logFileName")
      fetchItem.url+ " fetch finished."
    } catch {
      case e: ClientProtocolException => "client redirect exception:" + fetchItem.url
      case e: UnknownHostException => "unknown url: " + fetchItem.url
      case e: HttpHostConnectException => "host can't connect exception:" + fetchItem.url
      case e: NoRouteToHostException => "no route to host exception:" + fetchItem.url
      case e: SocketException => "socket exception:" + fetchItem.url
      case e: NoHttpResponseException => "no http response exception: " + fetchItem.url
    }
  }

  def filterFetchedItem(item: FetchItem): Boolean = {
    this.synchronized {
      val hashUrl = Utils.hashString(item.url)
      if (FETCH_ITEM_CACHE.containsKey(hashUrl)) {
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
    filterFetchItemByUrlRegex(item.url, item.urlRegex.get) && filterFetchedItem(item)
  }


  def asyncFetch(fetchItem: FetchItem): Future[String] = {
    Future {
      blocking {
        fetch(fetchItem)
      }
    }
  }

  override def receive: Receive = {
    case fetchItem: FetchItem if filter(fetchItem) =>
      asyncFetch(fetchItem)
    case fetchItems: List[_] =>
      fetchItems.asInstanceOf[List[FetchItem]]
        .filter(f => filter(f))
        .foreach(fetchItem => asyncFetch(fetchItem))
    case _ => log.info("item has been fetched!!!")
  }
}
