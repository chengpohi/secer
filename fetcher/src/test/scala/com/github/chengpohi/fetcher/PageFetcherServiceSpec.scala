package scala.com.github.chengpohi.fetcher

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.bugogre.crawler.builder.FetchItemBuilder
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 *
 * Created by xiachen on 2/28/15.
 */
class PageFetcherServiceSpec(_system: ActorSystem)
  extends TestKit(_system) with FlatSpecLike
  with Matchers
  with BeforeAndAfterAll
  with ImplicitSender {
  val fetchItem: FetchItem = FetchItemBuilder.build()

  val pageFetcher = system.actorOf(Props[PageFetcherService], "page-fetcher")

  def this() = this(ActorSystem("PageFetcherServiceSpec"))

  override def afterAll(): Unit = Await.ready(system.terminate(), Duration.Inf)

  it should "start fetcher" in {
    pageFetcher ! fetchItem
    expectMsg(s"${fetchItem.url.toString} has been sended to child fetcher to fetch.")
  }
}
