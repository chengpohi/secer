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
    posts.size should be(1)
    //println(posts.size)
    val head = posts.head
    head.id should be(Some(4))
    head.tags.size should be(5)
    println("url = " + head.url)
    println("id = " + head.id)
    println("title = " + head.title)
    println("body = " + head.body)
    println("tags = " + head.tags)
  }
}
