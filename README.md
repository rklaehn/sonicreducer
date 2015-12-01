# SonicReducer

## Overview

This is a tiny library for reducing arrays or traversables in an hierarchical way. This can turn O(n^2) operations into O(n log(n)) operations in some cases.

Imagine you had to concatenate a large number of strings without using a StringBuilder. The most straightforward way to do this would be `strings.reduce(_ + _)`. But that is O(n<sup>2</sup>). Using hierarchical reduction, this can be done in O(n log(n)) without too much effort `Reducer.reduce(strings)(_ + _).get`.

***Of course this assumes that the operation used for reduction is associative.***

When reducing arrays, simple indexing is used. When reducing traversables, an internal buffer of size 32 is used. This is big enough for collections of up to 2^32 elements. There is also a lower level stateful API.

```
Array(1,2,3,4,5,6,7,8).reduceLeft(_ + _)

(((((((1+2)+3)+4)+5)+6)+7)+8)

Array(1,2,3,4,5,6,7,8).reduceRight(_ + _)

(1+(2+(3+(4+(5+(6+(7+8)))))))

Reducer.reduceArray(Array(1,2,3,4,5,6,7,8))(_ + _)
((1+2)+(3+4))+((5+6)+(7+8))
```

### Benchmarks

See the [Benchmarks](src/test/com/rklaehn/sonicreducer/SonicReducerBench.scala) for examples on how to use. To run the benchmarks, use `sbt sonicReducerJVM/test:run`.

Here is an example for summing the rational numbers 1/1 + 1/2 + 1/3 + 1/4 + ...

```
val th = Thyme.warmed(warmth = Thyme.HowWarm.BenchOff, verbose = println)
val rationals = (0 until 1000).map(i ⇒ Rational(1, i + 1))
th.pbenchOffWarm("sum 1000 Rationals 1/x")(th.Warm(rationals.reduce(_ + _)))(th.Warm(Reducer.reduce(rationals)(_ + _).get))
```

Results:
```
Benchmark comparison (in 5.757 s): sum 1000 Rationals 1/x
Significantly different (p ~= 0)
  Time ratio:    0.04779   95% CI 0.04699 - 0.04858   (n=20)
    First     34.63 ms   95% CI 34.43 ms - 34.84 ms
    Second    1.655 ms   95% CI 1.629 ms - 1.681 ms
```


### Copyright and License

All code is available to you under the Apache 2 license, available at
http://www.apache.org/licenses/LICENSE-2.0.txt and also in the
[LICENSE](LICENSE) file.

Copyright Rüdiger Klaehn, 2015.
