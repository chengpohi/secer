package com.github.chengpohi.so

import java.io.{File, FileWriter}

/**
  * Created by xiachen on 05/01/2017.
  */
object SOFileSplitter {
  def main(args: Array[String]): Unit = {
    val answers = splitAnswers()
    val questions = splitQuestion()
    println("answers: " + answers)
    println("questions: " + questions)
    println("Total: " + (answers + questions))
  }

  def splitQuestion(): Int = {
    val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    //val file = new File("/Users/xiachen/IdeaProjects/secer/plugins/so/src/test/resources/so.xml")
    var fileWriter: FileWriter = null
    val MAX_LINE = 1000000
    var i = 0
    scala.io.Source.fromFile(file).getLines()
      .filter(_.contains("PostTypeId=\"1\"")).zipWithIndex.foreach(line => {
      println("write: " + line._2)
      if (line._2 % MAX_LINE == 0) {
        fileWriter =
          new FileWriter(new File(s"/Users/xiachen/IdeaProjects/data/questions-${line._2 / MAX_LINE}.xml"))
      }
      fileWriter.write(line._1 + System.lineSeparator())
      i = i + 1
    })
    i
  }

  def splitAnswers(): Int = {
    val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    //val file = new File("/Users/xiachen/IdeaProjects/secer/plugins/so/src/test/resources/so.xml")
    var fileWriter: FileWriter = null
    val MAX_LINE = 1000000
    var i = 0
    scala.io.Source.fromFile(file).getLines()
      .filter(_.contains("ParentId=")).zipWithIndex.foreach(line => {
      println("write: " + line._2)
      if (line._2 % MAX_LINE == 0) {
        fileWriter =
          new FileWriter(new File(s"/Users/xiachen/IdeaProjects/data/answers-${line._2 / MAX_LINE}.xml"))
      }
      i = i + 1
      fileWriter.write(line._1)
    })
    i
  }
}
