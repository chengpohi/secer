package com.github.chengpohi.so

import java.io.File

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.github.chengpohi.indexer.{Finished, PageIndexerService}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ArrayBuffer

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

    var ids = new ArrayBuffer[Int]()

    val f = (s: String) => s.contains(filterTag) && !s.contains("ParentId=")

    val actorSystem = ActorSystem("Crawler", ConfigFactory.load("crawler"))
    val soExtractor = actorSystem.actorOf(Props(new SOExtractorApp()))
    val posts = SOExtractor().extract(file)(f)
    posts.filter(_.tags.contains(filterTag))
      .foreach(post => {
        post.setIndexType(filterTag)
        ids.append(post.Id)
        soExtractor ! post
      })
    println("Index Questions Finished!")
    println("-" * 10)
    println("Index Answers!")

    val f2 = (s: String) => s.contains("ParentId=") && ids.exists(id => s.contains(id.toString))
    val answers = SOExtractor().extract(file)(f2)
    answers.filter(_.parentId.isDefined)
      .filter(a => ids.contains(a.parentId.get))
      .foreach(answer => {
        answer.setIndexType("answer")
        soExtractor ! answer
      })

    println("Index Answers Finished!")

    actorSystem.terminate()
  }
}

