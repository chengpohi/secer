package com.github.chengpohi.app.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import com.github.chengpohi.app.config.CrawlerConfig
import org.apache.logging.log4j.{LogManager, Logger}

class HttpWebServer(implicit system: ActorSystem) {
  private val logger: Logger = LogManager.getLogger(HttpWebServer.getClass)
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  def start(route: Route): Unit = {
    Http().bindAndHandle(route, CrawlerConfig.CRAWLER_HTTP_IP, CrawlerConfig.CRAWLER_HTTP_PORT)
    logger.info(s"Server online at http://${CrawlerConfig.CRAWLER_HTTP_IP}:${CrawlerConfig.CRAWLER_HTTP_PORT}/")
  }
}

object HttpWebServer {
  def response(content: String): StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, content))
  }
}
