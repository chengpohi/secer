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
  val actorSystem = ActorSystem("Crawler", ConfigFactory.load("crawler"))
  val soExtractor = actorSystem.actorOf(Props(new SOExtractorApp()))

  def indexQuestion(file: File)(f: String => Boolean, filterTag: String): List[Int] = {
    val posts = SOExtractor().extract(file)(f)

    posts.filter(_.tags.contains(filterTag)).map(post => {
      post.setIndexType(filterTag)
      soExtractor ! post
      post.Id
    }).toList
  }

  def indexAnswers(file: File, ids: List[Int]): List[Int] = {
    val posts = SOExtractor().extract(file)((s: String) => true)

    posts.filter(post => ids.binarySearch(post.Id) > -1).map(post => {
      post.setIndexType("answer")
      soExtractor ! post
      post.Id
    }).toList
  }

  def main(args: Array[String]): Unit = {
    val file = new File("/Users/xiachen/IdeaProjects/data/")
    //val file = new File("/Users/xiachen/IdeaProjects/secer/plugins/so/src/test/resources/so.xml")

    val filterTag = "java"
    val fl = (s: String) => s.contains(filterTag)

    val ids: List[Int] = file.listFiles()
      .filter(_.getName.contains("question-"))
      .par
      .flatMap(f => {
        indexQuestion(f)(fl, filterTag)
      }).toList.sorted

    println("-" * 10)
    println("Index Questions Finished!")
    println("-" * 10)

    val answers: List[Int] = file.listFiles()
      .filter(_.getName.contains("answer-"))
      .par
      .flatMap(f => {
        indexAnswers(f, ids)
      }).toList
    println("-" * 10)
    println("Index Questions Finished!")
    println("-" * 10)

    println("Total questions: " + ids.size)
    println("Total answers: " + answers.size)


    Thread.sleep(30000)
    Await.result(actorSystem.terminate(), Duration.Inf)
  }
}

