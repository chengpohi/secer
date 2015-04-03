package org.bugogre.crawler.builder

import org.bugogre.crawler.indexer.FieldSelector
import org.bugogre.crawler.url.FetchItem

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
    FetchItem("http://stackoverflow.com", "turing", "stackoverflow", fields)
  }
}
