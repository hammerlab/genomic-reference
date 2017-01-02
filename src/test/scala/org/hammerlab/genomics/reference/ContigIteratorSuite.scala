package org.hammerlab.genomics.reference

import org.hammerlab.genomics.reference.test.LocusUtil
import org.hammerlab.genomics.reference.test.region._
import org.hammerlab.test.Suite

class ContigIteratorSuite
  extends Suite
    with LocusUtil {

  test("simple") {
    ContigIterator(
      Seq(
        ("chr1", 100, 200, 2),
        ("chr1", 110, 210, 1),
        ("chr2", 100, 200, 3)
      )
    ).toSeq should ===(
      Seq(
        ("chr1", 100, 200),
        ("chr1", 100, 200),
        ("chr1", 110, 210)
      )
    )
  }

  test("next past end") {
    val it =
      ContigIterator(
        Seq(
          ("chr1", 100, 200, 1),
          ("chr1", 110, 210, 1),
          ("chr2", 100, 200, 3)
        )
      )

    it.hasNext === true
    it.next() === ("chr1", 100, 200)
    it.hasNext === true
    it.next() === ("chr1", 110, 210)
    it.hasNext === false
    intercept[NoSuchElementException] {
      it.next()
    }
  }
}
