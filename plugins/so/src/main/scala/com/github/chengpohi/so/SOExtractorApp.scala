package com.github.chengpohi.so

import java.io.File
import java.util.concurrent.Executors

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.github.chengpohi.indexer.{Finished, PageIndexerService}
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}

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
  val actorSystem = ActorSystem("Crawler", ConfigFactory.load("crawler"))
  val soExtractor = actorSystem.actorOf(Props(new SOExtractorApp()))
  val filterTag = "java"

  def main(args: Array[String]): Unit = {
    val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    //val file = new File("/Users/xiachen/IdeaProjects/secer/plugins/so/src/test/resources/so.xml")
    var ids = Array.fill(5000 * 10000)(false)
    val filterTag = "java"

    SOExtractor()
      .extract(file)
      .foreach(post => {
        post.postType match {
          case Some(1) if post.tags.contains(filterTag) =>
            ids(post.Id) = true
            post.setIndexType(filterTag)
            soExtractor ! post
          case Some(2) if post.parentId.exists(id => ids(id)) =>
            post.setIndexType("answer")
            soExtractor ! post
          case _ =>
        }
      })
  }
}

