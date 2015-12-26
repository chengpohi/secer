package org.bugogre.crawler.parser.impl

/**
 * seccrawler
 * Created by chengpohi on 12/25/15.
 */
object EnWikiParser {
  val START_TAG = "<page>"
  val END_TAG = "</page>"
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
          sb.toString() + self.next()
        }

        def hasNext = {
          while(self.hasNext && !start(self.head))
            self.next()
          self.hasNext
        }

        def next() = {
          findCloseTag()
        }
      }
    }
  }
}
