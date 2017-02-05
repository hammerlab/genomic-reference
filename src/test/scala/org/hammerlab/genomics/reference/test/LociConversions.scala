package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.Locus
import org.hammerlab.test.implicits.Conversions

/**
 * Implicit helpers for converting [[Int]]s to [[Locus]]s.
 *
 * Use in tests by importing `LociConversions._`.
 */
case object LociConversions extends Conversions[Int, Locus] {
  implicit def intToLocus(locus: Int): Locus = Locus(locus)
}
