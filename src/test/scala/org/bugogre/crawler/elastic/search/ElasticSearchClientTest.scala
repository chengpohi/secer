package org.bugogre.crawler.elastic.search

import org.scalatest.FlatSpec

/**
 * Created by xiachen on 4/3/15.
 */
class ElasticSearchClientTest extends FlatSpec{
  "elastic search client" should " search successfully" in {
    val result = ElasticSearchClient.searchUrl("turing", "stackoverflow", "http://stackoverflow.com/")
    println(result.getSource.get("_date"))
    assert(result != null)
  }
}
