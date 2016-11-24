package com.github.chengpohi.parser.impl

import com.github.chengpohi.HDSLInterpreter
import com.github.chengpohi.model._
import com.github.chengpohi.parser.url.UrlNormalizer
import com.github.chengpohi.util.Utils._
import org.jsoup.nodes.{Document, Element}

import scala.collection.JavaConverters._

/**
  * Created by xiachen on 3/1/15.
  */
class HtmlPageParser {
  lazy val m = java.security.MessageDigest.getInstance("MD5")

  def normalize(url: String): String = UrlNormalizer.normalize(url)

  val SIG_NUM: Int = 1
  val HEX: Int = 16

  def hash(s: String): String = {
    val b = s.getBytes("UTF-8")
    m.update(b, 0, b.length)
    new java.math.BigInteger(SIG_NUM, m.digest()).toString(HEX)
  }

  def hrefs(doc: Document, item: FetchItem): List[FetchItem] = {
    item.bfs match {
      case Some(true) =>
        doc.select("a").asScala.toList
          .map(e => e.absUrl("href"))
          .filter(e => e.length != 0)
          .map(e => FetchItem(normalize(e),
            item.indexName, item.indexType, item.selectors, item.urlRegex)
          )
      case Some(false) => List()
    }
  }


  def parse(doc: Document, item: FetchItem): (IndexItem, List[FetchItem]) = {
    val fields: Map[String, Any] = parseBySelector(doc, item.selectors)
    (IndexItem(doc, item, hashString(doc.html), fields), hrefs(doc, item))
  }

  def selectBySelector(doc: Document, selector: String): String = {
    doc.select(selector).first() match {
      case el: Element => el.html()
      case _ => ""
    }
  }

  def parseBySelector(doc: Document, fieldSelectors: String): Map[String, Any] = {
    val interpreter: HDSLInterpreter = new HDSLInterpreter(doc)
    interpreter.intercept(fieldSelectors)
  }
}
