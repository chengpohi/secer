
package org.bugogre.crawler.html

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import scala.collection.JavaConverters._

/**
 * Created by xiachen on 3/26/15.
 */
class HtmlToMarkdown {
  val HR: String = "hr"
  val HEADER = List("h1", "h2", "h3", "h4", "h5", "h6")
  val NEW_LINE: String = " "

  def parse(html: String): String = {
    val doc = Jsoup.parse(html)
    parseHeader(doc)
    parseHR(doc)
    parseHref(doc)
    doc.text().replaceAll(NEW_LINE, "\r\n\r\n")
  }

  def replaceBlockQuotes(html: String): String = {
    null
  }

  def parseParagraph(doc: Document) = {
    doc.getElementsByTag("p").asScala.map(h => {
      val content = NEW_LINE + h.text() + NEW_LINE
      h.empty().append(content)
    })
  }

  def parseHref(doc: Document) = {
    doc.getElementsByTag("a").asScala.map(h => {
      val content = "[" + h.text() + "](" + h.attr("href") + ")"
      h.empty().append(content)
    })
  }

  def parseHR(doc: Document) = {
    doc.getElementsByTag(HR).asScala.map(h => {
      h.append(getHr)
    })
  }

  def parseHeader(doc: Document) = {
    HEADER.map(t =>
      doc.getElementsByTag(t).asScala.map(h => {
        val content = getTitle(t) + h.text() + NEW_LINE
        h.empty().append(content)
      })
    )
  }

  def getHr(): String = NEW_LINE + "-------" + NEW_LINE

  def getTitle(title: String): String = title match {
    case "h1" => "#"
    case "h2" => "##"
    case "h3" => "###"
    case "h4" => "####"
    case "h5" => "#####"
    case "h6" => "######"
    case _ => "#"
  }
}
