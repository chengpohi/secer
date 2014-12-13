package org.bugogre.crawler.url

import org.bugogre.crawler.exception.UrlIllegalException

/**
 * Created by xiachen on 12/13/14.
 */
case class Url(url: String) {
  val urlRegex = """^http.*""".r
  url match {
    case urlRegex() =>
    case _ => throw new UrlIllegalException("Url is Illegal")
  }
}
