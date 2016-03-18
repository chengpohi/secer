package com.github.chengpohi.parser.impl

import scala.io.Source

/**
 * seccrawler
 * Created by chengpohi on 12/25/15.
 */
case class Page(title: String, redirectTitle: String, text: String, contributor: String, model: String,
                timestamp: String,
                format: String,
                comment: String) {
}

object EnWikiParser {

  import IteratorHelper._

  import scala.xml.XML.loadString

  val START_TAG = "<page>"
  val END_TAG = "</page>"

  def main(args: Array[String]): Unit = {
    val pages = Source.fromFile("/Users/xiachen/IdeaProjects/wikipedia/tmp").getLines()
      .sliceByPredicate(l => START_TAG.equals(l.trim), l => END_TAG.equals(l.trim))

    pages.foreach(xmlPage => {
      val p = loadString(xmlPage)
      val title: String = (p \\ "title").text
      val redirectTitle: String = (p \\ "redirect" \\ "@title").text
      val text: String = (p \\ "revision" \\ "text").text
      val contributor: String = (p \\ "revision" \\ "contributor" \\ "username").text
      val timestamp: String = (p \\ "revision" \\ "timestamp").text
      val model: String = (p \\ "model").text
      val format: String = (p \\ "format").text
      val comment: String = (p \\ "revision" \\ "comment").text

      val page = Page(title, redirectTitle, text, contributor, model, timestamp, format, comment)
    })
  }
}
