package org.bugogre.crawler.util

import org.json4s.ParserUtil.ParseException
import org.scalatest.FlatSpec

/**
 * JSONUtilTest
 * Created by chengpohi on 7/20/15.
 */
class JSONUtilTest extends FlatSpec {
  val fieldSelectorStr = """ [{"field": "_title", "selector": "title"}, {"field": "_question", "selector": "div.question  div.post-text"}] """

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
}
