package org.bugogre.crawler.parser.impl

import org.scalatest.FlatSpec

import scala.io.Source

/**
 * seccrawler
 * Created by chengpohi on 12/26/15.
 */
class EnWikiParserTest extends FlatSpec {

  import EnWikiParser._

  "SliceByPredicate " should " slice iterator by predicate" in {
    val pages: List[String] =
      Source.fromURL(getClass.getResource("/pages.xml")).getLines()
        .sliceByPredicate(l => START_TAG.equals(l.trim), l => END_TAG.equals(l.trim)).toList
    assert(pages.size == 3)
    assert(pages.head.trim.startsWith(START_TAG))
    assert(pages.head.trim.endsWith(END_TAG))
  }
  "SliceByPredicate " should " slice iterator by predicate in empty tag without error" in {
    val pages: List[String] =
      Source.fromURL(getClass.getResource("/emptyPage.xml")).getLines()
        .sliceByPredicate(l => START_TAG.equals(l.trim), l => END_TAG.equals(l.trim)).toList
    assert(pages.isEmpty)
  }
}
