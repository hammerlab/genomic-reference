package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.hammerlab.genomics.reference.ContigName.Normalization.Lenient
import org.hammerlab.macros.Conversions

/**
 * Implicit helpers for converting [[String]]s to [[ContigName]]s, with lenient checking of constructed [[ContigName]]s.
 */
@Conversions[String, ContigName]
trait LenientContigNameConversions {
  implicit def factory: ContigName.Factory = Lenient
}
