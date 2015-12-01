## SonicReducer

### Overview

This is a tiny library for reducing sequences in an hierarchical way. I mainly published this to teach myself how to
publish to maven, but you might nevertheless find it useful.

```
Array(1,2,3,4).reduceLeft(_ + _)

(((1+2)+3)+4)

Array(1,2,3,4).reduceRight(_ + _)

(1+(2+(3+4)))

Reducer.reduceArray(Array(1,2,3,4))

(1+2)+(3+4)
```

### Getting SonicReducer



### Examples

### Copyright and License

All code is available to you under the Apache 2 license, available at
http://www.apache.org/licenses/LICENSE-2.0.txt and also in the
[LICENSE](LICENSE) file.

Copyright Rüdiger Klaehn, 2015.
