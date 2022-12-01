package com.rklaehn.sonicreducer

import spire.math.Rational

object BenchFixture {
  val charArrays: List[Array[Char]] = (0 until 1000).map(_.toString.toCharArray).toList
  val strings: List[String] = (0 until 1000).map(_.toString).toList
  val rationals: List[Rational] = (0 until 1000).map(i => Rational(1, i + 1)).toList
}
