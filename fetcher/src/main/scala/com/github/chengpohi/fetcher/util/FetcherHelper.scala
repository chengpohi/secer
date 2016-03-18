package com.github.chengpohi.fetcher.util

/**
 * secer
 * Created by chengpohi on 3/18/16.
 */
object FetcherHelper {
  def hashString(source: String): String =
    java.security.MessageDigest.getInstance("MD5").digest(source.getBytes).map(0xFF & _).map {
      "%02x".format(_)
    }.foldLeft("") {
      _ + _
    }
}
