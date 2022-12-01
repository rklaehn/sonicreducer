package com.rklaehn.sonicreducer

import org.openjdk.jmh.annotations._
import spire.math.Rational

@State(Scope.Benchmark)
class StandardReducerBench {
  @Benchmark
  def concatenate1000ArrayChar(): Array[Char] = {
    BenchFixture.charArrays.reduce(_ ++ _)
  }

  @Benchmark
  def concatenate1000String(): String = {
    BenchFixture.strings.reduce(_ + _)
  }

  @Benchmark
  def sum1000Rationals1perX(): Rational = {
    BenchFixture.rationals.reduce(_ + _)
  }
}
