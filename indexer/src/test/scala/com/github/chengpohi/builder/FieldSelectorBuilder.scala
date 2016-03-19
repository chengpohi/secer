package com.github.chengpohi.builder

import com.github.chengpohi.model.FieldSelector

/**
 * secer
 * Created by chengpohi on 3/19/16.
 */
object FieldSelectorBuilder {
  val fieldSelector = FieldSelector("div", "div")
  val fieldSelectors = List(FieldSelector("div1", "div1"), FieldSelector("div2", "div2"))
}
