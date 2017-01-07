package com.github.chengpohi.so

import java.io.File

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by xiachen on 28/12/2016.
  */
class SOExtractorTest extends FlatSpec with Matchers {
  it should "parse row to post model" in {
    val file = new File(getClass.getResource("/so.xml").toURI)
    //val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    val posts = SOExtractor().extract(file)
    //println(posts.size)
    val head = posts.next()
    head.Id should be(4)
    head.tags.size should be(6)
    head.doc.size should be(17)
    println("url = " + head.url)
    println("id = " + head.Id)
    println("title = " + head.title)
    println("body = " + head.body)
    println("tags = " + head.tags)
    println("fields = " + head.doc)
  }
  it should "performance test" in {
    val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    //val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    //val file = new File(getClass.getResource("/questions-0.xml").toURI)
    val posts = SOExtractor().extract(file)
    val startTime = System.currentTimeMillis()
    val length = 10000

    val results = posts
      .take(length)
      .filter(p => p.tags.contains("java") && p.postType.contains(1))
      .map(p => {
        p.Id
      }).toList

    val endTime = System.currentTimeMillis()
    val averageTime = (length.toDouble * 1000) / (endTime - startTime)

    println("Results Size: " + results.size)
    println("Total Parsed: " + length)
    println("Total Time: " + (endTime - startTime) / 1000 + "s")
    println("Average Per Time: " + averageTime + "/s")
  }
}
