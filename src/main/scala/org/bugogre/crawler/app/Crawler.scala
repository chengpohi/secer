package org.bugogre.crawler.app

import akka.actor._
import com.typesafe.config.ConfigFactory
import org.bugogre.crawler.config.SecConfig
import org.bugogre.crawler.fetcher._
import org.bugogre.crawler.url.FetchItem
import org.slf4j.LoggerFactory

import scala.concurrent.duration.Duration

/**
 * author: chengpohi@gmail.com
 */
object Crawler{
  def main(args: Array[String]) {
    val system = ActorSystem("Crawler", ConfigFactory.load("crawler"))

    val remoteHostPort = SecConfig.crawlerPort
    val remotePath = s"akka.tcp://Crawler@$remoteHostPort/user/pagefetcher"

    system.actorOf(Props(new Crawler(remotePath)), "crawler")
  }

  sealed trait Echo
  case object Start extends Echo
  case object Done extends Echo
}

class Crawler(path: String) extends Actor {
  import org.bugogre.crawler.app.Crawler._

  val pageFetcher = context.actorOf(Props[PageFetcher], "PageFetcher")
  val LOG = LoggerFactory.getLogger(getClass.getName)

  override def preStart(): Unit = {
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
    case ActorIdentity(`path`, None)        => LOG.error(s"remote actor not found $path")
    case ReceiveTimeout                     => sendIdentifyRequest()
  }

  def active(actor: ActorRef): Receive = {
    case Start => {
      actor ! FetchItem("http://www.baidu.com")
    }
    case Done =>
  }
}
