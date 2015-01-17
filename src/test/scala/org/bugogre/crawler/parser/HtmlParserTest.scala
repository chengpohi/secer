package org.bugogre.crawler.parser

import akka.actor.{Actor, Props, ActorSystem}
import org.bugogre.crawler.httpclient.Web
import org.bugogre.crawler.url.Url
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 12/16/14.
 */
class HtmlParserTest extends FlatSpec{
  val htmlStr: String = "<html><head><title>First parse</title></head><body><p>Parsed HTML into a doc.</p></body></html>"

  "HtmlParser " should " parse successfully html by div " in {
  }
}
