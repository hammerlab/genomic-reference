package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.hammerlab.genomics.reference.ContigName.Strict
import org.hammerlab.macros.Conversions

/**
 * Implicit helpers for converting [[String]]s to [[ContigName]]s.
 */
@Conversions[String, ContigName]
trait ContigNameConversions {
  implicit def factory: ContigName.Factory = Strict
}

object ContigNameConversions
  extends ContigNameConversions
