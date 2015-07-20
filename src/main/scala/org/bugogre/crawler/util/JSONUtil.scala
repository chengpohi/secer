package org.bugogre.crawler.util

import com.secer.elastic.model.FieldSelector

import org.json4s._
import org.json4s.native.JsonMethods._

/**
 * JSONUtil is mainly used to parse string to secer model or secer model to string.
 * Created by chengpohi on 7/20/15.
 */
object JSONUtil {
  implicit val formats = DefaultFormats
  def fieldSelectorParser(fieldSelectorStr: String): List[FieldSelector] = parse(fieldSelectorStr).extract[List[FieldSelector]]
}