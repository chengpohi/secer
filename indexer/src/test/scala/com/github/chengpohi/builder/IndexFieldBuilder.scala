package com.github.chengpohi.builder

import com.github.chengpohi.model.IndexField

/**
 * secer
 * Created by chengpohi on 3/19/16.
 */
object IndexFieldBuilder {
  val indexField = IndexField("field", "value")
  val indexFields = List(IndexField("field1", "value1"), IndexField("field2", "value2"), IndexField("field3", "value3"))
}
