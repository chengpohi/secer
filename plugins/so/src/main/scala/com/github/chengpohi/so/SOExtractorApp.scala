package com.github.chengpohi.so

import java.io.File

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.github.chengpohi.indexer.{Finished, PageIndexerService}
import com.typesafe.config.ConfigFactory

/**
  * Created by xiachen on 30/12/2016.
  */
class SOExtractorApp extends Actor with ActorLogging {
  private val indexer = context.actorOf(Props[PageIndexerService], "indexer")

  def receive: Receive = {
    case post: Post => {
      indexer ! post
    }
    case Finished => {
    }
  }
}

object SOExtractorApp {
  def main(args: Array[String]): Unit = {
    val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    //val file = new File("/Users/xiachen/IdeaProjects/secer/plugins/so/src/test/resources/so.xml")
    val filterTag = "java"

    val f = (s: String) => s.contains(filterTag) && !s.contains("ParentId=")

    val posts = SOExtractor().extract(file)(f)
    val actorSystem = ActorSystem("Crawler", ConfigFactory.load("crawler"))
    val soExtractor = actorSystem.actorOf(Props(new SOExtractorApp()))
    posts.foreach(post => {
      post.setIndexType(filterTag)
      soExtractor ! post
    })
    println("Index Finished!")
    actorSystem.terminate()
  }
}
