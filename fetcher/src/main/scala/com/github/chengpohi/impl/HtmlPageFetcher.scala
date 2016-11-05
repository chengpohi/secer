package com.github.chengpohi.impl

import com.github.chengpohi.model.FetchItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory


/**
  * Page Fetcher
  * Created by xiachen on 3/1/15.
  */
class HtmlPageFetcher {
  val log = LoggerFactory.getLogger(getClass.getName)

  def fetch(fetchItem: FetchItem): Option[(FetchItem, Document)] = {
    val result: (FetchItem, Document, Int) = connect(fetchItem)
    result._3 match {
      case 200 => Some((result._1, result._2))
      case _ => None
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
}
