package org.hammerlab.genomics.reference

import org.hammerlab.genomics.bases.{ Base, Bases, BasesBuffer }

trait ContigSequence extends BasesBuffer {
  override type LengthT = NumLoci
  def apply(locus: Locus): Base
  def slice(start: Locus, length: Int): Bases
  def length: NumLoci
}
