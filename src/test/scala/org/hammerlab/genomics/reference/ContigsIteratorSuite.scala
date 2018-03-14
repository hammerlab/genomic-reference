package org.hammerlab.genomics.reference

import hammerlab.test.Suite
import org.hammerlab.genomics.reference.test.ClearContigNames
import org.hammerlab.genomics.reference.test.region._

class ContigsIteratorSuite
  extends Suite
    with ClearContigNames {

  implicit def convertTuple(t: (String, List[(String, Int, Int)])): (ContigName, List[Region]) = (t._1, t._2)
  implicit def convertContigs(in: List[(String, List[(String, Int, Int)])]): List[(ContigName, List[Region])] =
    in.map(convertTuple)

  test("simple") {
    ContigsIterator(
      Seq(
        ("chr1", 10, 20),
        ("chr1", 30, 40),
        ("chr2", 50, 60),
        ("chr3", 70, 80)
      )
    ).map { case (contigName, contigRegions) ⇒ contigName → contigRegions.toList }.toList should ===(
      List(
        "chr1" ->
          List(
            ("chr1", 10, 20),
            ("chr1", 30, 40)
          ),
        "chr2" ->
          List(
            ("chr2", 50, 60)
          ),
        "chr3" ->
          List(
            ("chr3", 70, 80)
          )
      )
    )
  }

  test("partially-consumed contig") {
    val it =
      ContigsIterator(
        Seq(
          ("chr1", 10, 20),
          ("chr1", 30, 40),
          ("chr2", 50, 60)
        )
      )

    val (chr1Name, chr1) = it.next()
    chr1Name === "chr1"
    chr1.next() === (("chr1", 10, 20))

    // Skip ahead to chr2 before exhausting chr1.
    val (chr2Name, chr2) = it.next()
    chr2Name === "chr2"
    chr2.toSeq === Seq(("chr2", 50, 60))
  }

  test("throw on repeat contigs") {
    intercept[RepeatedContigException] {
      ContigsIterator(
        Seq(
          ("chr1", 10, 20),
          ("chr1", 30, 40),
          ("chr2", 50, 60),
          ("chr1", 70, 80)
        )
      ).toList
    }
  }
}
