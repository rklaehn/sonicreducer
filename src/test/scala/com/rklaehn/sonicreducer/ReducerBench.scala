package com.rklaehn.sonicreducer
import ichi.bench._
import spire.math._

object ReducerBench extends App {

  val th = Thyme.warmed(warmth = Thyme.HowWarm.BenchOff, verbose = println)

  val charArrays = (0 until 1000).map(_.toString.toCharArray)
  th.pbenchOffWarm("concatenate 1000 Array[Char]")(th.Warm(charArrays.reduce(_ ++ _)))(th.Warm(Reducer.reduce(charArrays)(_ ++ _).get))

  val strings = (0 until 1000).map(_.toString)
  th.pbenchOffWarm("concatenate 1000 String")(th.Warm(strings.reduce(_ + _)))(th.Warm(Reducer.reduce(strings)(_ + _).get))

  val rationals = (0 until 1000).map(i => Rational(1, i + 1))
  th.pbenchOffWarm("sum 1000 Rationals 1/x")(th.Warm(rationals.reduce(_ + _)))(th.Warm(Reducer.reduce(rationals)(_ + _).get))

}
