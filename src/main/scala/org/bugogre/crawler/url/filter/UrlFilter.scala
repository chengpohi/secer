package org.bugogre.crawler.url.filter

import org.bugogre.crawler.rule.Rule

/**
 * Created by xiachen on 4/3/15.
 */
object UrlFilter {
  lazy val UrlRegex = """^http.*""".r

  def filterByRule(url: String, rule: Rule): Boolean = {
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

  def filterByTime(url: String, rule: Rule): Boolean = {
    true
  }
}
