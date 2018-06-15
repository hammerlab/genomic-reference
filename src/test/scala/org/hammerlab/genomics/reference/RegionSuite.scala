package org.hammerlab.genomics.reference

import hammerlab.test.Suite
import org.hammerlab.genomics.reference.test.ClearContigNames
import org.hammerlab.genomics.reference.test.LociConversions._

class RegionSuite
  extends Suite
    with ClearContigNames {

  test("overlapping reads") {
    val read1 = Region("1", 1, 9)
    val read2 = Region("1", 5, 13)

    read1.overlaps(read2) === true
    read2.overlaps(read1) === true
  }

  test("overlapping reads, different contigs") {
    val read1 = Region("1", 1, 9)
    val read2 = Region("2", 5, 13)

    read1.overlaps(read2) === false
    read2.overlaps(read1) === false
  }

  test("nonoverlapping reads") {
    val read1 = Region("1", 1, 9)
    val read2 = Region("1", 10, 18)

    read1.overlaps(read2) === false
    read2.overlaps(read1) === false
  }

  test("overlapping reads on start") {
    val read1 = Region("1", 1, 9)
    val read2 = Region("1", 8, 16)

    read1.overlaps(read2) === true
    read2.overlaps(read1) === true
  }

  test("read completely covers another") {
    val read1 = Region("1", 1, 9)
    val read2 = Region("1", 5, 8)

    read1.overlaps(read2) === true
    read2.overlaps(read1) === true
  }

  test("regions overlapsLocus") {
    val read = Region("1", 5, 8)
    ==(read.overlapsLocus(4), false)
    ==(read.overlapsLocus(5), true)
    ==(read.overlapsLocus(6), true)
    ==(read.overlapsLocus(7), true)
    ==(read.overlapsLocus(8), false)
    ==(read.overlapsLocus(9), false)
  }

  test("regions overlapsLocus with window 1") {
    val read = Region("1", 5, 8)
    ==(read.overlapsLocus( 3, 1), false)
    ==(read.overlapsLocus( 4, 1), true)
    ==(read.overlapsLocus( 5, 1), true)
    ==(read.overlapsLocus( 6, 1), true)
    ==(read.overlapsLocus( 7, 1), true)
    ==(read.overlapsLocus( 8, 1), true)
    ==(read.overlapsLocus( 9, 1), false)
    ==(read.overlapsLocus(10, 1), false)
  }

  test("regions overlapsLocus with window 2") {
    val read = Region("1", 5, 8)
    ==(read.overlapsLocus( 2, 2), false)
    ==(read.overlapsLocus( 3, 2), true)
    ==(read.overlapsLocus( 4, 2), true)
    ==(read.overlapsLocus( 5, 2), true)
    ==(read.overlapsLocus( 6, 2), true)
    ==(read.overlapsLocus( 7, 2), true)
    ==(read.overlapsLocus( 8, 2), true)
    ==(read.overlapsLocus( 9, 2), true)
    ==(read.overlapsLocus(10, 2), false)
  }
}
