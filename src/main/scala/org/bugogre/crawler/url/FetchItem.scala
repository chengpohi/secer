package org.bugogre.crawler.url

import org.bugogre.crawler.rule.Rule

/**
 * Created by xiachen on 12/13/14.
 */
case class FetchItem(url: String) {
  lazy val UrlRegex = """^http.*""".r

  def filterByRule(rule: Rule): Boolean = {
    url match {
      case UrlRegex() =>
      case _ => return false
    }

    rule.rules.map(p => {
      url match {
        case p() => return true
        case _ =>
      }
    })
    false
  }
}
