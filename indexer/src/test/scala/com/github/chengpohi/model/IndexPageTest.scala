package com.github.chengpohi.model

import com.github.chengpohi.builder.IndexPageBuilder
import org.scalatest.FlatSpec

/**
 * secer
 * Created by chengpohi on 3/19/16.
 */
class IndexPageTest extends FlatSpec{
  "IndexPage " should " be parsed to DocumentMap" in {
    val indexPage: IndexPage = IndexPageBuilder.indexPage
    val map = indexPage.map
    assert(map.contains("url"))
    assert(map.contains("md5"))
    assert(map.contains("urlMd5"))
    assert(map.contains("created_at"))
  }
}
