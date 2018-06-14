package org.hammerlab.genomics.reference

import cats.data.Ior
import hammerlab.iterator._
import hammerlab.test.Suite
import org.hammerlab.cmp.CanEq
import org.hammerlab.genomics.reference.test.ClearContigNames
import org.hammerlab.genomics.reference.test.region._

class ContigsIteratorSuite
  extends Suite
    with ClearContigNames {

  implicit def cmpKey[
    LK, LV,
    RK, RV
  ](
    implicit
    k: CanEq[LK, RK],
    v: CanEq[LV, RV]
  ):
    CanEq.Aux[
      (LK, LV),
      (RK, RV),
      Ior[k.Diff, v.Diff]
    ] =
    new CanEq[(LK, LV), (RK, RV)] {
      type Diff = Ior[k.Diff, v.Diff]
      def cmp(l: (LK, LV), r: (RK, RV)): Option[Ior[k.Diff, v.Diff]] =
        Ior.fromOptions(
          k(l._1, r._1),
          v(l._2, r._2)
        )
    }

  test("simple") {
    ==(
      ContigsIterator(
        Seq(
          ("chr1", 10, 20),
          ("chr1", 30, 40),
          ("chr2", 50, 60),
          ("chr3", 70, 80)
        )
      )
      .mapValues { _.toList }
      .toList,
      List(
        "chr1" →
          List(
            ("chr1", 10, 20),
            ("chr1", 30, 40)
          ),
        "chr2" →
          List(
            ("chr2", 50, 60)
          ),
        "chr3" →
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
    ==(chr1Name, "chr1")
    ==(chr1.next(), ("chr1", 10, 20))

    // Skip ahead to chr2 before exhausting chr1.
    val (chr2Name, chr2) = it.next()
    ==(chr2Name, "chr2")
    ==(chr2.toSeq, Seq(("chr2", 50, 60)))
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
      )
      .toList
    }
  }
}
