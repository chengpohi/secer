package org.bugogre.crawler.app
import org.bugogre.crawler.httpclient._

/**
 * author: chengpohi@gmail.com
 */
object Crawler {
  def main(args: Array[String]) {
    val url = "http://www.baidu.com"
    val httpresponse = new HttpResponse(url)
    println(httpresponse.response.url)
    println("-----------------------")
    println(httpresponse.response.body)
  }
}
