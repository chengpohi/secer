package com.github.chengpohi.app

import akka.actor._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.routing.FromConfig
import com.github.chengpohi.app.http.HttpWebServer
import com.github.chengpohi.app.http.HttpWebServer.response
import com.github.chengpohi.model.FetchItem
import com.github.chengpohi.registry.ELKCommandRegistry
import com.github.chengpohi.{ELKInterpreter, PageFetcherService}
import com.typesafe.config.ConfigFactory
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.language.postfixOps

/**
  * author: chengpohi@gmail.com
  */
object Crawler {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("Crawler", ConfigFactory.load("crawler"))
    val http = new HttpWebServer()
    system.actorOf(Props(new Crawler(http)), "crawler")
  }
}

class Crawler(webServer: HttpWebServer) extends Actor with ActorLogging {
  implicit val formats = org.json4s.DefaultFormats
  lazy val replInterpreter = new ELKInterpreter(ELKCommandRegistry)
  val fetcher = context.actorOf(FromConfig.props(Props[PageFetcherService]), "fetcher")
  val route: Route = {
    path("/") {
      get {
        response("<h1>Welcome by Secer</h1>")
      }
    } ~ path("seed") {
      post {
        entity(as[String]) { fetchItem =>
          val item: FetchItem = parse(fetchItem).extract[FetchItem]
          self ! item
          response("<h1>Get Your Seed</h1>")
        }
      }
    } ~ path("repl") {
      post {
        entity(as[String]) { dsl =>
          val result: String = replInterpreter.run(dsl)
          response(result)
        }
      }
    }
  }

  override def preStart(): Unit = webServer.start(route)

  def receive: Receive = {
    case fetchItem: FetchItem =>
      fetcher ! fetchItem
    case Terminated(_) =>
      context.unbecome()
    case str: String =>
      log.info(str)
  }
}
