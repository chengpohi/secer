package org.bugogre.crawler.app

import akka.actor._
import com.secer.elastic.model.FetchItem
import com.typesafe.config.ConfigFactory
import org.bugogre.crawler.config.SecConfig
import org.bugogre.crawler.util.JSONUtil
import org.elasticsearch.common.netty.handler.codec.http.HttpResponseStatus.OK
import org.elasticsearch.common.netty.util.CharsetUtil
import org.siny.web.app.Siny
import org.siny.web.response.HttpResponse
import org.siny.web.rest.controller.RestController._
import org.siny.web.session.HttpSession
import org.slf4j.LoggerFactory

import scala.concurrent.duration.Duration

/**
 * author: chengpohi@gmail.com
 */
object Crawler extends Siny {
  def main(args: Array[String]) {
    val system = ActorSystem("Crawler", ConfigFactory.load("crawler"))

    val remoteHostPort = SecConfig.CRAWLER_PORT
    val remotePath = s"akka.tcp://Crawler@$remoteHostPort/user/pagefetcher"

    system.actorOf(Props(new Crawler(remotePath)), "crawler")
  }


}

sealed trait Echo

case object Start extends Echo

case object Done extends Echo

class Crawler(path: String) extends Actor with Siny {
  val LOG = LoggerFactory.getLogger(getClass.getName)

  override def preStart(): Unit = {
    this.initialize()
  }

  sendIdentifyRequest()

  def sendIdentifyRequest(): Unit =
    context.actorSelection(path) ! Identify(path)

  def receive = identifying

  def identifying: Receive = {
    case ActorIdentity(`path`, Some(actor)) =>
      LOG.info("Connected the remote actor")
      context.watch(actor)
      context.become(active(actor))
      context.setReceiveTimeout(Duration.Undefined)
      self ! Start
    case ActorIdentity(`path`, None) => LOG.error(s"remote actor not found $path")
    case ReceiveTimeout => sendIdentifyRequest()
    case str: String => {
      LOG.info(str)
    }
  }

  def active(actor: ActorRef): Receive = {
    case Start =>
    case fetchItem: FetchItem => {
      actor ! fetchItem
    }
    case str: String => {
      LOG.info(str)
    }
    case Done =>
  }

  override def registerPath(): Unit = {
    registerHandler("POST", "/crawler", postFetchItem)
  }

  def postFetchItem(httpSession: HttpSession): HttpResponse = {
    val rawFetchItem = httpSession.httpRequest.getContent.toString(CharsetUtil.UTF_8)

    val fetchItem = JSONUtil.fetchItemParser(rawFetchItem)

    self ! fetchItem

    HttpResponse("Hi Programmer, I get ur url: " + fetchItem.url.toString, OK)
  }
}
