package org.hammerlab.genomics.reference

import org.hammerlab.genomics.reference.test.TestRegion
import org.scalatest.{FunSuite, Matchers}

class RegionSuite
  extends FunSuite
    with Matchers {

  test("overlapping reads") {
    val read1 = TestRegion("1", 1, 9)
    val read2 = TestRegion("1", 5, 13)

    read1.overlaps(read2) should be(true)
    read2.overlaps(read1) should be(true)
  }

  test("overlapping reads, different contigs") {
    val read1 = TestRegion("1", 1, 9)
    val read2 = TestRegion("2", 5, 13)

    read1.overlaps(read2) should be(false)
    read2.overlaps(read1) should be(false)
  }

  test("nonoverlapping reads") {
    val read1 = TestRegion("1", 1, 9)
    val read2 = TestRegion("1", 10, 18)

    read1.overlaps(read2) should be(false)
    read2.overlaps(read1) should be(false)
  }

  test("overlapping reads on start") {
    val read1 = TestRegion("1", 1, 9)
    val read2 = TestRegion("1", 8, 16)

    read1.overlaps(read2) should be(true)
    read2.overlaps(read1) should be(true)
  }

  test("read completely covers another") {
    val read1 = TestRegion("1", 1, 9)
    val read2 = TestRegion("1", 5, 8)

    read1.overlaps(read2) should be(true)
    read2.overlaps(read1) should be(true)
  }
}