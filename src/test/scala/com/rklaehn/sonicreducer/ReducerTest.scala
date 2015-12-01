package com.rklaehn.sonicreducer

import org.scalatest.FunSuite

class ReducerTest extends FunSuite {
  val test = Array(1, 2, 3, 4)

  test("reduceArray") {
    assert(Reducer.reduceArray(Array.empty[Int])(_ + _).isEmpty)
    assert(1 === Reducer.reduceArray(Array(1))(_ + _).get)
    assert(test.reduce(_ + _) === Reducer.reduceArray(test)(_ + _).get)
  }

  test("reduceTraversable") {
    assert(test.reduce(_ + _) === Reducer.reduce(test)(_ + _).get)
  }
}
