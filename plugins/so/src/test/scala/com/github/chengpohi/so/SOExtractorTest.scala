package com.github.chengpohi.so

import java.io.File

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by xiachen on 28/12/2016.
  */
class SOExtractorTest extends FlatSpec with Matchers {
  it should "parse row to post model" in {
    val file = new File(getClass.getResource("/so.xml").toURI)
    val posts = SOExtractor().extract(file)
    posts.size should be(1)
    println(posts.head)
  }
}
