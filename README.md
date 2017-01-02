# genomic-reference
Utilities for representing and working with reference genomes.

[![Build Status](https://travis-ci.org/hammerlab/genomic-reference.svg?branch=master)](https://travis-ci.org/hammerlab/genomic-reference)
[![Coverage Status](https://coveralls.io/repos/github/hammerlab/genomic-reference/badge.svg?branch=master)](https://coveralls.io/github/hammerlab/genomic-reference?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/org.hammerlab.genomics/reference_2.11.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Creference)

Contains type-aliases and classes representing [reference loci, contig names,](src/main/scala/org/hammerlab/genomics/reference/package.scala) and intervals ([with](src/main/scala/org/hammerlab/genomics/reference/Region.scala) and [without](src/main/scala/org/hammerlab/genomics/reference/Interval.scala) contigs attached).

The former use [Scala value-classes](http://docs.scala-lang.org/overviews/core/value-classes.html) to obtain a measure of type-safety without run-time overhead.

### Scala 2.1[01].x support
Due to [a bug in Scala 2.10's value-class support](https://issues.scala-lang.org/browse/SI-10118), [the `master` branch](https://github.com/hammerlab/genomic-reference/tree/master) builds against Scala 2.11.x only, and Scala 2.11.x is considered the default target for this library.
 
Scala 2.10.x is supported on [the `2.10` branch](https://github.com/hammerlab/genomic-reference/tree/2.10), which uses `case class`es instead of value-classes, therefore incurring some run-time performance-penalty.

If you're curious, the differences between the branches can be viewed from the CLI:

```bash
git diff 2.10..master
```
