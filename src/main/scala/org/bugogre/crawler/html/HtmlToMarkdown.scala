
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
  val CONTENT_NEW_LINE: String = "!@#"
  val TABLE_NEW_LINE: String = "%%%"
  val NONE_TAG_NAME: String = "NONE"
  val TABLE_SPLIT_LINE: String = " | "
  val CODE_LINE : String = "~~~"
  val STRONG_SYMBOL : String = "**"
  val STRONG_ITALIC : String = "***"
  val BLOCK_QUOTE: String = ">"
  val THEAD_LINE : String = "-----"

  def parse(html: String): String = {
    val doc = Jsoup.parse(html)
    parseStrong(doc)
    parseHeader(doc)
    parseHR(doc)
    parseHref(doc)
    //parseDiv(doc)
    parseParagraph(doc)
    parseBlockQuotes(doc)
    parseUL(doc)
    parseTable(doc)
    parseCode(doc)
    doc.text()
      .replaceAll(NEW_LINE, "\r\n\r\n")
      .replaceAll(TABLE_NEW_LINE, "\r\n")
      .replaceAll(CONTENT_NEW_LINE, " ")
  }

  def parseCode(doc: Document) = {
    doc.getElementsByTag("code").asScala.map(h => {
      val content = CODE_LINE + TABLE_NEW_LINE + filterSpace(h.text()) + TABLE_NEW_LINE + CODE_LINE + NEW_LINE
      h.html(content)
    })
  }

  def parseStrong(doc: Document) = {
    doc.getElementsByTag("strong").asScala.map(h => {
      val content = STRONG_SYMBOL + filterSpace(h.text()) + STRONG_SYMBOL
      h.html(content)
    })

    doc.getElementsByTag("strong").asScala.map(h => {
      h.getElementsByTag("em").asScala.map(e => {
        val content = STRONG_ITALIC + filterSpace(e.text()) + STRONG_ITALIC
        h.html(content)
      })
    })
  }

  def parseBlockQuotes(doc: Document) = {
    doc.getElementsByTag("blockquote").asScala.map(h => {
      val content = NEW_LINE + BLOCK_QUOTE + CONTENT_NEW_LINE + filterSpace(h.text()) + NEW_LINE
      h.html(content)
    })
  }

  def parseDiv(doc: Document) = {
    doc.getElementsByTag("div").asScala.map(h => {
      val content = NEW_LINE + filterSpace(h.text()) + NEW_LINE
      h.html(content)
    })
  }

  def parseTable(doc: Document) = {
    doc.getElementsByTag("table").asScala.map(h => {
      val thead = h.getElementsByTag("thead").first()
        .getElementsByTag("tr").first()
        .getElementsByTag("th")


      val header = {
        val h = for (t <- thead.asScala) yield filterSpace(t.text())
        val split = for (s <- h) yield THEAD_LINE
        filterSpace(h.mkString(TABLE_SPLIT_LINE)) + TABLE_NEW_LINE + filterSpace(split.mkString(TABLE_SPLIT_LINE)) + TABLE_NEW_LINE
      }

      val content = for (tr <- h.getElementsByTag("tbody").first().getElementsByTag("tr").asScala) yield {
        val t = for (t <- tr.getElementsByTag("td").asScala) yield filterSpace(t.text())
        filterSpace(t.mkString(TABLE_SPLIT_LINE))
      }

      h.html(header + content.mkString(TABLE_NEW_LINE) + NEW_LINE)
    })
  }

  def parseUL(doc: Document) = {
    doc.getElementsByTag("ul").asScala.map(h => {
      h.getElementsByTag("li").asScala.map(l => {
        val content = "-" + CONTENT_NEW_LINE + filterSpace(l.text())
        l.html(content)
      })
    })
  }

  def parseParagraph(doc: Document) = {
    doc.getElementsByTag("p").asScala.map(h => {
      h.getElementsByTag("code").asScala.map(c => {
        c.html("`" + c.text() + "`")
      })
      val content = NEW_LINE + filterSpace(h.text()) + NEW_LINE
      h.html(content)
    })
  }

  def parseImage(doc: Document) = {
    doc.getElementsByTag("img").asScala.map(h => {
      val content = "![" + h.attr("alt") + "]" + "(" + h.attr("src") + ")"
      h.html(content)
    })
  }

  def parseHref(doc: Document) = {
    doc.getElementsByTag("a").asScala.map(h => {
      val content = "[" + filterSpace(h.text()) + "](" + h.attr("href") + ")"
      h.html(content)
    })
  }

  def parseHR(doc: Document) = {
    doc.getElementsByTag(HR).asScala.map(h => {
      h.html(getHr)
    })
  }

  def parseHeader(doc: Document) = {
    HEADER.map(t =>
      doc.getElementsByTag(t).asScala.map(h => {
        val content = getTitle(t) + filterSpace(h.text()) + NEW_LINE
        h.html(content)
      })
    )
  }

  def filterSpace(str: String): String = str.replaceAll(NEW_LINE, CONTENT_NEW_LINE)

  def getHr: String = NEW_LINE + "-----" + NEW_LINE

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
