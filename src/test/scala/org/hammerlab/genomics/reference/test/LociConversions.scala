package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.Locus
import org.hammerlab.macros.Conversions

/**
 * Implicit helpers for converting [[Int]]s to [[Locus]]s.
 *
 * Use in tests by importing `LociConversions._`.
 */
@Conversions[Int, Locus]
trait LociConversions {
  implicit def intToLocus(locus: Int): Locus = Locus(locus)
  implicit def lociRangeToSeq(range: Range): Seq[Locus] = range.map(Locus(_))
  implicit def lociRangeToIterator(range: Range): Iterator[Locus] = range.iterator.map(Locus(_))
}

object LociConversions extends LociConversions
