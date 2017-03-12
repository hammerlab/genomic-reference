package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.hammerlab.genomics.reference.ContigName.Strict.InconsistentContigNamesException
import org.hammerlab.test.Suite

class ContigNameSuite
  extends Suite
    with LenientContigNameConversions
    with ClearContigNames {

  test("permissive normalization") {
    Vector(
      ContigName("1") → 123,
      ContigName("chr1") → 456,
      ContigName("2") → 789
    )
    .groupBy(_._1)
    .mapValues(_.map(_._2)) should be(
      Map(
        ContigName("1") → Iterable(123, 456),
        ContigName("2") → Iterable(789)
      )
    )
  }

  test("permissive option lifting") {
    Option(ContigName("chr1")) should === (Some("1"))
  }

  test("permissive map conversion") {
    Map(
      ContigName("1") → 123,
      ContigName("2") → 456
    ) should ===(
      Map(
        "chr2" → 456,
        "chr1" → 123
      )
    )
  }

  test("strict normalization") {
    import ContigName.Strict
    val _ = ContigName("1")
    intercept[InconsistentContigNamesException] {
      ContigName("chr1")
    }
  }
}
