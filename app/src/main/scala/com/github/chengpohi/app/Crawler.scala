package com.github.chengpohi.app

import java.util.concurrent.TimeUnit

import akka.actor.SupervisorStrategy.Restart
import akka.actor._
import com.github.chengpohi.app.config.CrawlerConfig
import com.github.chengpohi.model.FetchItem
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * author: chengpohi@gmail.com
 */
object Crawler {
  def main(args: Array[String]) {
    val system = ActorSystem("Crawler", ConfigFactory.load("crawler"))

    val remoteHostPort = CrawlerConfig.CRAWLER_PORT
    val remotePath = s"akka.tcp://Crawler@$remoteHostPort/user/page-fetcher"

    system.actorOf(Props(new Crawler(remotePath)), "crawler")
  }


}

sealed trait Echo

case object Start extends Echo

case object Done extends Echo

class Crawler(path: String) extends Actor{
  val LOG = LoggerFactory.getLogger(getClass.getName)

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: Exception => {
        LOG.info("INFO")
        Restart
      }
    }

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
    case ActorIdentity(`path`, None) => {
      LOG.error(s"remote actor not found $path")
      TimeUnit.SECONDS.sleep(3)
      sendIdentifyRequest()
    }
    case ReceiveTimeout => sendIdentifyRequest()
    case str: String =>
      LOG.info(str)
  }

  def active(actor: ActorRef): Receive = {
    case Start =>
    case fetchItem: FetchItem => {
      actor ! fetchItem
    }
    case Terminated(_) => {
      context.unbecome()
      sendIdentifyRequest()
    }
    case str: String => {
      LOG.info(str)
    }
    case Done =>
  }
}
