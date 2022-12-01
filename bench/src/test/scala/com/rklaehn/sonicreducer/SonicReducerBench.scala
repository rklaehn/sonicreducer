package com.rklaehn.sonicreducer

import org.openjdk.jmh.annotations._
import spire.math.Rational

@State(Scope.Benchmark)
class SonicReducerBench {
  @Benchmark
  def concatenate1000ArrayChar(): Array[Char] = {
    Reducer.reduce(BenchFixture.charArrays)(_ ++ _).get
  }

  @Benchmark
  def concatenate1000String(): String = {
    Reducer.reduce(BenchFixture.strings)(_ + _).get
  }

  @Benchmark
  def sum1000Rationals1perX(): Rational = {
    Reducer.reduce(BenchFixture.rationals)(_ + _).get
  }
}
