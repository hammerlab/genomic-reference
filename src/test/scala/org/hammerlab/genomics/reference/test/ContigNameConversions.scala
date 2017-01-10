package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.hammerlab.test.implicits.Conversions

/**
 * Implicit helpers for converting [[String]]s to [[ContigName]]s.
 *
 * Use in tests by importing `ContigNameConversions._`.
 */
case object ContigNameConversions extends Conversions[String, ContigName]
