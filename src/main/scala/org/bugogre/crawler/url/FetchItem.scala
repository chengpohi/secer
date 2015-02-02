package org.bugogre.crawler.url

import org.bugogre.crawler.exception.UrlIllegalException
import org.bugogre.crawler.rule.Rule

/**
 * Created by xiachen on 12/13/14.
 */
case class FetchItem(url: String) {
  val UrlRegex = """^http.*""".r
  url match {
    case UrlRegex() =>
    case _ => throw new UrlIllegalException("Url is Illegal")
  }

  def filterByRule(rule: Rule): Boolean = {
    rule.rule.map(p => {
      url match {
        case p() => return true
        case _ =>
      }
    })
    false
  }
}
