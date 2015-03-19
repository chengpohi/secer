package org.bugogre.crawler.nlp

import breeze.linalg._
import breeze.stats.mean

/**
 * Created by xiachen on 3/15/15.
 */
object ScalaNlp {
  def main(args: Array[String]) = {
    val x = DenseVector(0, 8, 12, 20)
    val y = DenseVector(8, 9, 11, 20)
    val dm = DenseMatrix((0.0, 8.0, 12.0, 20.0), (8.0, 9.0, 11.0, 12.0))
    val m = mean(dm)
    println(dm(0, ::))
    println(dm.cols)
  }
}
