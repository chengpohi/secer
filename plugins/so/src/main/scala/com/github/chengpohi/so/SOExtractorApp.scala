package com.github.chengpohi.so

import java.io.File
import java.util.concurrent.Semaphore

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.github.chengpohi.indexer.{Finished, PageIndexerService}
import com.typesafe.config.ConfigFactory

/**
  * Created by xiachen on 30/12/2016.
  */
class SOExtractorApp(semaphore: Semaphore) extends Actor with ActorLogging {
  private val indexer = context.actorOf(Props[PageIndexerService], "indexer")

  def receive: Receive = {
    case post: Post => {
      indexer ! post
    }
    case Finished => {
      semaphore.release()
    }
  }
}

object SOExtractorApp {
  def main(args: Array[String]): Unit = {
    val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    //val file = new File("/Users/xiachen/IdeaProjects/secer/plugins/so/src/test/resources/so.xml")
    val semaphore = new Semaphore(5000)
    val posts = SOExtractor().extract(file)
    val actorSystem = ActorSystem("Crawler", ConfigFactory.load("crawler"))
    val soExtractor = actorSystem.actorOf(Props(new SOExtractorApp(semaphore)))
    posts.filter(_.tags.contains("java")).foreach(post => {
      soExtractor ! post
    })
  }
}
