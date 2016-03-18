package scala.com.github.chengpohi.fetcher

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

/**
 * seccrawler
 * Created by chengpohi on 8/26/15.
 */
class HtmlPageFetcherTest  (_system: ActorSystem)
  extends TestKit(_system)
  with FlatSpecLike
  with Matchers
  with BeforeAndAfterAll
  with ImplicitSender {
  val htmlPageFetcher = new HtmlPageFetcher(null, null)

  val urlFilter = List(
    (".*", "http://www.google.com", true),
    ("http://www.google.com/.*", "http://www.google.com", false),
    ("http://stackoverflow.com/.*", "http://stackoverflow.com/questions/4636610/how-to-pattern-match-using-regular-expression-in-scala", true),
    ("http://stackoverflow.com/questions/\\d+/.*", "http://stackoverflow.com/questions/4636610/how-to-pattern-match-using-regular-expression-in-scala", true)
  )


  "Html Page Fetcher " should "filter href by regex" in {
    urlFilter.foreach(t => {
      assert(htmlPageFetcher.filterFetchItemByUrlRegex(t._2, t._1) == t._3)
    })
  }}
