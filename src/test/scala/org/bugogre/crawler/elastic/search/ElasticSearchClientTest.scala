package org.bugogre.crawler.elastic.search

import org.scalatest.FlatSpec

/**
 * Created by xiachen on 4/3/15.
 */
class ElasticSearchClientTest extends FlatSpec {
  "elastic search client" should " search successfully" in {
    val result = ElasticSearchClient.searchUrl("turing", "stackoverflow", "http://stackoverflow.com/")
    println(result.getSource.get("_date"))
    assert(result != null)
  }

  "elastic search client" should " search md5 and urlMd5" in {
    val result = ElasticSearchClient.comparePage("turing", "stackoverflow",
      "2ea85319277a7b4080a7e08ae462230e",
      "ad52f12a680bc1584dc4221f93b7e09a")
    assert(result != null)
  }
}
