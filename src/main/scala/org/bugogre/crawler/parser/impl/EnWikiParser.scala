package org.bugogre.crawler.parser.impl

import scala.io.Source

/**
 * seccrawler
 * Created by chengpohi on 12/25/15.
 */
object EnWikiParser {

  implicit class PredicateSliceIterator(it: Iterator[String]) {
    def sliceByPredicate(start: String => Boolean, end: String => Boolean): Iterator[String] = {
      val self = it.buffered
      new Iterator[String] {
        var sb = new StringBuilder("")
        private def findCloseTag(): String = {
          sb = new StringBuilder("")
          while (self.hasNext && !start(self.head))
            self.next()
          while(self.hasNext && !end(self.head))
            sb.append(self.next())
          if (self.hasNext) {
            sb.toString() + self.next()
          } else {
            ""
          }
        }

        def hasNext = {
          self.hasNext
        }

        def next() = {
          findCloseTag()
        }
      }
    }
  }

  val START_TAG = "<page>"
  val END_TAG = "</page>"

  def main(args: Array[String]): Unit = {
    Source.fromFile("./test.xml").getLines().sliceByPredicate(START_TAG.equals, END_TAG.equals).foreach(println)
  }
}
