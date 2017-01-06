package com.github.chengpohi.util

import scala.collection.mutable.ArrayBuffer

/**
  * secer
  * Created by chengpohi on 3/18/16.
  */
object Utils {
  def hashString(source: String): String =
    java.security.MessageDigest.getInstance("MD5").digest(source.getBytes).map(0xFF & _).map {
      "%02x".format(_)
    }.foldLeft("") {
      _ + _
    }

  implicit class ListUtil(ar: List[Int]) {
    def binarySearch(key: Int): Int = {
      def bi(start: Int, end: Int): Int = {
        if (end - start >= 0) {
          val index = (end - start) / 2 + start
          index match {
            case t if t < 0 => -1
            case t if t >= ar.length => -1
            case _ =>
              findNext(key, bi, start, end, index)
          }
        } else {
          -1
        }

      }

      bi(0, ar.length - 1)
    }

    private def findNext(key: Int, bi: (Int, Int) => Int, start: Int, end: Int, index: Int) = {
      val id = ar(index)
      id match {
        case `key` => index
        case a if id > key => bi(start, index - 1)
        case a if id < key => bi(index + 1, end)
      }
    }
  }

}
