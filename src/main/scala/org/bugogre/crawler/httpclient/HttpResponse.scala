package org.bugogre.crawler.httpclient


sealed trait Response {
  val url: String
  val body: String
}

class HttpResponse(u: String) {
}
