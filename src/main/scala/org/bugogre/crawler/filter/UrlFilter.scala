package org.bugogre.crawler.filter

import com.secer.elastic.search.ElasticSearchClient
import org.bugogre.crawler.config.SecConfig
import org.bugogre.crawler.rule.Rule
import org.elasticsearch.indices.IndexMissingException
import org.elasticsearch.search.SearchHit

/**
 * Created by xiachen on 4/3/15.
 */
object UrlFilter {
  lazy val UrlRegex = """^http.*""".r

  lazy val rule = Rule(SecConfig.EXCLUDE_URL_PATTERNS)

  def filterByRule(url: String): Boolean = {
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

  def filterByTime(url: String, indexName: String, indexType: String): Boolean = {
    try {
      ElasticSearchClient.searchUrl(indexName, indexType, url) match {
        case null => false
        case result: SearchHit => {
          if (System.currentTimeMillis() - 24 * 60 * 60 * 1000 >= result.getSource.get("_date").asInstanceOf[String].toLong)
            false
          else
            true
        }
      }
    } catch {
      case e: IndexMissingException => false
    }
  }
}
