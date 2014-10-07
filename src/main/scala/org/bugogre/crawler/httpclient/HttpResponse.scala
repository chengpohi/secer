package org.bugogre.crawler.httpclient


trait Response {
  val url: String
  val body: String
}

class HttpResponse(u: String) {
  val url = u
  val body = WebFactory.getEntityToStr(u)
}
