package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.Locus
import org.scalactic.CanEqual

trait LocusCanEqualInt {
  implicit val locusIntEqual =
    new CanEqual[Locus, Int] {
      override def areEqual(a: Locus, b: Int): Boolean = a.locus == b
    }
}
