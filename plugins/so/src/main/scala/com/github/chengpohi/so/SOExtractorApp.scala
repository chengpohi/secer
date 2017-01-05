package com.github.chengpohi.so

import java.io.File
import java.util
import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.github.chengpohi.indexer.{Finished, PageIndexerService}
import com.github.chengpohi.util.Utils
import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ArrayBuffer
import Utils._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

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

    val actorSystem = ActorSystem("Crawler", ConfigFactory.load("crawler"))
    val soExtractor = actorSystem.actorOf(Props(new SOExtractorApp()))

    val filterTag = "java"
    var ids = new ArrayBuffer[Int]()
    val f = (s: String) => s.contains(filterTag) || s.contains("ParentId=")
    val posts = SOExtractor().extract(file)(f)

    posts.filter(post => {
      if (post.tags.isEmpty) {
        filterAnswers(ids, post)
      } else {
        post.tags.contains(filterTag)
      }
    }).foreach(post => {
      updateIndexType(ids, filterTag, post)
      soExtractor ! post
    })

    println("-" * 10)
    println("Index Questions Finished!")
    println("-" * 10)
    Thread.sleep(5000)
    Await.result(actorSystem.terminate(), Duration.Inf)
  }

  private def updateIndexType(ids: ArrayBuffer[Int], filterTag: String, post: Post) = {
    post.parentId match {
      case Some(_) =>
        post.setIndexType("answer")
      case _ =>
        ids.append(post.Id)
        post.setIndexType(filterTag)
    }
  }

  private def filterAnswers(ids: ArrayBuffer[Int], post: Post): Boolean = {
    post.parentId match {
      case Some(id) =>
        ids.binarySearch(id) match {
          case -1 => false
          case _ => true
        }
      case None => false
    }
  }
}

