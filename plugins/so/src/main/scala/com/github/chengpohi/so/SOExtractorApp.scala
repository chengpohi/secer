package com.github.chengpohi.so

import java.io.File
import java.util.concurrent.Executors

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.github.chengpohi.indexer.{Finished, PageIndexerService}
import com.github.chengpohi.util.Utils._
import com.typesafe.config.ConfigFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

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
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(3))

  def indexQuestion(file: File, filterTag: String): Future[List[Boolean]] = {
    val fl = (s: String) => s.contains(filterTag)

    var ar = Array.fill(5000 * 10000)(false)
    Future.sequence(file.listFiles()
      .toList
      .filter(_.getName.contains("questions-"))
      .map(f => {
        Future {
          val posts = SOExtractor().extract(f, fl)
          posts.filter(_.tags.contains(filterTag)).foreach(post => {
            post.setIndexType(filterTag)
            soExtractor ! post
            ar(post.Id) = true
          })
        }
      })).map(_ => ar.toList)
  }

  def indexAnswers(file: File, ids: List[Boolean]): Future[(List[Boolean], List[Int])] = {
    Future.sequence(
      file.listFiles()
        .toList
        .filter(_.getName.contains("answers-"))
        .map(f => {
          Future {
            val posts = SOExtractor().extract(f)
            posts.filter(post => ids(post.Id)).map(post => {
              post.setIndexType("answer")
              soExtractor ! post
              post.Id
            }).toList
          }
        })).map(i => (ids, i.flatten))
  }

  def main(args: Array[String]): Unit = {
    val file = new File("/Users/xiachen/IdeaProjects/data/")
    //val file = new File("/Users/xiachen/IdeaProjects/secer/plugins/so/src/test/resources/so.xml")

    val res = indexQuestion(file, filterTag).flatMap(ids => {
      println("-" * 10)
      println("Index Questions Finished!")
      println("-" * 10)
      indexAnswers(file, ids)
    })
    res.onComplete {
      case Success(r) => {
        println("-" * 10)
        println("Index Questions Finished!")
        println("-" * 10)
        println("Total questions: " + r._1.count(_ == true))
        println("Total answers: " + r._2.size)
      }
      case Failure(f) => {
        f.printStackTrace()
        println("index fail:" + f.getMessage)
      }
    }
  }
}

