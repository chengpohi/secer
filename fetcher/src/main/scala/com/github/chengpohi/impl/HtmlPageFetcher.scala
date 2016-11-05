package com.github.chengpohi.impl

import java.net.{NoRouteToHostException, SocketException, UnknownHostException}
import java.util.concurrent.Executors

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.github.chengpohi.config.FetcherConfig
import com.github.chengpohi.model.FetchItem
import org.apache.http.NoHttpResponseException
import org.apache.http.client.ClientProtocolException
import org.apache.http.conn.HttpHostConnectException
import org.jsoup.nodes.Document
import org.jsoup.{HttpStatusException, Jsoup}

import scala.concurrent._


/**
  * Page Fetcher
  * Created by xiachen on 3/1/15.
  */
class HtmlPageFetcher(pageParser: ActorRef, fetchItem: FetchItem) extends Actor with ActorLogging {
  def fetch(fetchItem: FetchItem): String = {
    try {
      log.info("Fetcher: {}, Fetch Url: {}", self.hashCode(), fetchItem.url)
      val result: (FetchItem, Document, Int) = connect(fetchItem)
      result._3 match {
        case 200 => {
          pageParser ! (result._1, result._2)
          fetchItem.url + " fetch finished."
        }
        case _ => s"Fail: ${fetchItem.url}, http request failed code: ${result._3}"
      }
    } catch {
      case e: ClientProtocolException => "client redirect exception:" + fetchItem.url
      case e: UnknownHostException => "unknown url: " + fetchItem.url
      case e: HttpHostConnectException => "host can't connect exception:" + fetchItem.url
      case e: NoRouteToHostException => "no route to host exception:" + fetchItem.url
      case e: SocketException => "socket exception:" + fetchItem.url
      case e: NoHttpResponseException => "no http response exception: " + fetchItem.url
      case e: HttpStatusException => "http status exception: " + e.getMessage
    }
  }

  def filter(item: FetchItem): Boolean = {
    item.url.matches(item.urlRegex.get)
  }

  def connect(item: FetchItem): (FetchItem, Document, Int) = {
    Thread.sleep(3000)
    val doc = Jsoup.connect(item.url).timeout(50000).execute()
    (item, doc.parse(), doc.statusCode())
  }

  override def receive: Receive = {
    case fetchItem: FetchItem if filter(fetchItem) => fetch(fetchItem)
    case fetchItems: List[_] =>
      fetchItems.asInstanceOf[List[FetchItem]]
        .filter(f => filter(f))
        .foreach(fetchItem => {
          self ! fetchItem
        })
    case fetchItem: FetchItem => {
      log.info("{} item has been fetched!!!", fetchItem.url)
    }
    case _ => {
      log.info("no idea what happened.")
    }
  }
}
