package org.bugogre.crawler.url

import org.bugogre.crawler.exception.UrlIllegalException
import org.bugogre.crawler.rule.Rule

/**
 * Created by xiachen on 12/13/14.
 */
case class Url(url: String) {
  val urlRegex = """^http.*""".r
  url match {
    case urlRegex() =>
    case _ => throw new UrlIllegalException("Url is Illegal")
  }

  def filterByRule(rule: Rule): Boolean = {
    rule.rule.foreach(p => {
      url match {
        case p() =>
        case _ => return false;
      }
    })
    true
  }
}
