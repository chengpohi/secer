package com.github.chengpohi.fetcher.cache

import java.util.concurrent.{ConcurrentHashMap, ConcurrentMap}

import com.github.chengpohi.fetcher.model.FetchItem
import com.secer.elastic.model.FetchItem

/**
 * seccrawler
 * Created by chengpohi on 8/23/15.
 */
object URLCache {
  val FETCH_ITEM_CACHE: ConcurrentMap[String, FetchItem] = new ConcurrentHashMap[String, FetchItem]()
}
