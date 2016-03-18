package com.github.chengpohi.parser.impl

/**
 * seccrawler
 * Created by chengpohi on 12/26/15.
 */
object IteratorHelper {
  implicit class PredicateSliceIterator(it: Iterator[String]) {
    def sliceByPredicate(start: String => Boolean, end: String => Boolean): Iterator[String] = {
      val self = it.buffered
      new Iterator[String] {
        var sb = new StringBuilder("")

        private def findCloseTag(): String = {
          sb = new StringBuilder("")
          while (self.hasNext && !start(self.head))
            self.next()
          while (self.hasNext && !end(self.head))
            sb.append(self.next())
          sb.toString() + self.next()
        }
        def hasNext = {
          while (self.hasNext && !start(self.head))
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
