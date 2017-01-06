package com.github.chengpohi.so

import java.io.File

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by xiachen on 28/12/2016.
  */
class SOExtractorTest extends FlatSpec with Matchers {
  it should "parse row to post model" in {

    val f = (s: String) => s.contains("java") && !s.contains("ParentId=")
    val file = new File(getClass.getResource("/so.xml").toURI)
    //val file = new File("/Users/xiachen/IdeaProjects/data/Posts.xml")
    val posts = SOExtractor().extract(file, f)
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

    val ids = Array(2)
    val f2 = (s: String) => s.contains("ParentId=") && ids.exists(id => s.contains(id.toString))
    val answers = SOExtractor().extract(file,f2)
    val answer = answers.next()
    println(answer.id)
  }
}
