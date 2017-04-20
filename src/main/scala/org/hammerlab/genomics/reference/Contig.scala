package org.hammerlab.genomics.reference

import org.hammerlab.genomics.bases.{ Base, Bases, BasesBuffer }

trait Contig
  extends BasesBuffer {
  override type LengthT = NumLoci
  def apply(locus: Locus): Base = slice(locus, 1).head
  def slice(start: Locus, length: Int): Bases
  def length: NumLoci
}
