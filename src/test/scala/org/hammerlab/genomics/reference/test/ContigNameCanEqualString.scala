package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.scalactic.CanEqual

trait ContigNameCanEqualString {
  implicit val contigNameStringEquals =
    new CanEqual[ContigName, String] {
      override def areEqual(a: ContigName, b: String): Boolean = a.name == b
    }
}
