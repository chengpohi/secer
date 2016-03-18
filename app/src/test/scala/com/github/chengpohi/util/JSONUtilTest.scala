package com.github.chengpohi.util

import com.github.chengpohi.app.util.JSONUtil
import org.json4s.ParserUtil.ParseException
import org.scalatest.FlatSpec

/**
 * JSONUtilTest
 * Created by chengpohi on 7/20/15.
 */
class JSONUtilTest extends FlatSpec {
  val fieldSelectorStr = """ [{"field": "_title", "selector": "title"}, {"field": "_question", "selector": "div.question  div.post-text"}] """
  val fieldFetchItemStr = """ { "url": "http://stackoverflow.com/questions/32152489/why-jar-files-do-not-contain-documentation", "indexName": "stackoverflow", "indexType": "stackoverflow", "selectors": [{"field": "_title", "selector": "title"}, {"field": "_question", "selector": "div.question  div.post-text"}], "urlRegex": ".*" }"""

  "JSONUtil fieldSelectorParser" should "extract json to list FieldSelectors" in {
    val fieldSelectors = JSONUtil.fieldSelectorParser(fieldSelectorStr)
    assert(fieldSelectors.length == 2)
    assert(fieldSelectors.head.field == "_title")
    assert(fieldSelectors.head.selector == "title")
    assert(fieldSelectors(1).field == "_question")
    assert(fieldSelectors(1).selector == "div.question  div.post-text")
  }

  "JSONUtil sss" should "extract json to list FieldSelectors" in {
    intercept[ParseException] {
      val fieldSelectors = JSONUtil.fieldSelectorParser("test invalid")
    }
  }

  "JSONUtil fetchItemParser" should "extract json to FetchItem" in {
    val fetchItem = JSONUtil.fetchItemParser(fieldFetchItemStr)
    assert(fetchItem.indexName == "stackoverflow")
    assert(fetchItem.indexType == "stackoverflow")
    assert(fetchItem.url.toString == "http://stackoverflow.com/questions/32152489/why-jar-files-do-not-contain-documentation")
  }
}
