package org.bugogre.crawler.util

import java.net.URL

import com.secer.elastic.model.{FetchItem, FieldSelector}
import org.json4s._
import org.json4s.native.JsonMethods._

/**
 * JSONUtil is mainly used to parse string to secer model or secer model to string.
 * Created by chengpohi on 7/20/15.
 */
object JSONUtil {
  implicit val formats = DefaultFormats
  def fieldSelectorParser(fieldSelectorStr: String): List[FieldSelector] = parse(fieldSelectorStr).extract[List[FieldSelector]]
  def fetchItemParser(fetchItemStr: String): FetchItem = {
    val f = parse(fetchItemStr)
    val url = f \\ "url"
    val indexName = f \\ "indexName"
    val indexType = f \\ "indexType"
    val selectors = f \\ "selectors"
    FetchItem(
      new URL(url.extract[String]),
      indexName.extract[String],
      indexType.extract[String],
      selectors.extract[List[FieldSelector]]
    )
  }
}
