package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.hammerlab.genomics.reference.ContigName.InconsistentContigNamesException
import org.hammerlab.genomics.reference.test.ContigNameConversions._
import org.hammerlab.test.Suite

class ContigNameSuite
  extends Suite
    with ClearContigNames {

  test("permissive normalization") {
    import org.hammerlab.genomics.reference.ContigName.Normalization.Lenient

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
    import org.hammerlab.genomics.reference.ContigName.Normalization.Lenient
    Option(ContigName("chr1")) should === (Some("1"))
  }

  test("permissive map conversion") {
    import org.hammerlab.genomics.reference.ContigName.Normalization.Lenient

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
    val _ = ContigName("1")
    intercept[InconsistentContigNamesException] {
      ContigName("chr1")
    }
  }
}
