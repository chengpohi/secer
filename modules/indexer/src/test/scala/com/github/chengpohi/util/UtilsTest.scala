package com.github.chengpohi.util

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by xiachen on 05/01/2017.
  */
class UtilsTest extends FlatSpec with Matchers {

  import Utils._

  it should "binary search" in {
    val a1 = List()
    a1.binarySearch(1) should be(-1)

    val a2 = List(1)
    a2.binarySearch(1) should be(0)

    val a3 = List(1, 2, 3)
    a3.binarySearch(1) should be(0)
    a3.binarySearch(2) should be(1)
    a3.binarySearch(3) should be(2)

    val a4 = List(1, 2, 3, 4)

    a4.binarySearch(1) should be(0)
    a4.binarySearch(2) should be(1)
    a4.binarySearch(3) should be(2)
    a4.binarySearch(4) should be(3)

    val a5 = List(30)
    a5.binarySearch(25) should be(-1)
  }
}
