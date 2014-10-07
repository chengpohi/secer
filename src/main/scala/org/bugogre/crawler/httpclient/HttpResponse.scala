package org.bugogre.crawler.httpclient

case class Response(url: String, body: String)
class HttpResponse(url: String) {
  lazy val response = Response(url, WebFactory.getEntityToStr(url))
}
