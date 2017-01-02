package org.hammerlab.genomics.reference

trait HasLocus extends Any {
  def locus: Locus
}

object HasLocus {
  implicit def hasLocusToLocus(hl: HasLocus): Locus = hl.locus

  def unapply(hl: HasLocus): Option[Locus] = Some(hl.locus)
}
