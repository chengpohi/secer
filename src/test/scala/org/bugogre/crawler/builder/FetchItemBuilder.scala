package org.bugogre.crawler.builder

import java.net.URL

import com.secer.elastic.model.{FetchItem, FieldSelector}

/**
 * Created by xiachen on 3/1/15.
 */
object FetchItemBuilder {
  def fetchItem = {
    val fields = List(
        FieldSelector("_title", "title"),
        FieldSelector("_answers", "div#answers"),
        FieldSelector("_question", "div.question")
      )
    FetchItem(new URL("http://stackoverflow.com"), "turing", "stackoverflow", fields)
  }
}
