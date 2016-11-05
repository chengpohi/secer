package com.github.chengpohi.builder

import com.github.chengpohi.model.IndexItem

/**
  * secer
  * Created by chengpohi on 3/19/16.
  */
object IndexPageBuilder {
  val indexPage = IndexItem(null, FetchItemBuilder.fetchItem, "test-md5", IndexFieldBuilder.indexFields)
}
