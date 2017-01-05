package com.github.chengpohi.util

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by xiachen on 05/01/2017.
  */
class UtilsTest extends FlatSpec with Matchers {

  import Utils._

  it should "binary search" in {
    var a1 = new ArrayBuffer[Int]()
    a1.binarySearch(1) should be(-1)

    var a2 = new ArrayBuffer[Int]()
    a2.append(1)
    a2.binarySearch(1) should be(0)

    var a3 = new ArrayBuffer[Int]()
    a3.append(1)
    a3.append(2)
    a3.append(3)
    a3.binarySearch(1) should be(0)
    a3.binarySearch(2) should be(1)
    a3.binarySearch(3) should be(2)

    var a4 = new ArrayBuffer[Int]()
    a4.append(1)
    a4.append(2)
    a4.append(3)
    a4.append(4)

    a4.binarySearch(1) should be(0)
    a4.binarySearch(2) should be(1)
    a4.binarySearch(3) should be(2)
    a4.binarySearch(4) should be(3)

    var a5 = new ArrayBuffer[Int]()
    a5.append(30)
    a5.binarySearch(25) should be(-1)
  }
}
