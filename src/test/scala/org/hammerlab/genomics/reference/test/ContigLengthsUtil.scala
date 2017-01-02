package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.{ ContigLengths, ContigName, NumLoci }

trait ContigLengthsUtil {
  implicit def longTupleToContigLength(t: (String, Long)): (ContigName, NumLoci) = (t._1, NumLoci(t._2))
  implicit def intTupleToContigLength(t: (String, Int)): (ContigName, NumLoci) = (t._1, NumLoci(t._2))

  def makeContigLengths(contigs: (ContigName, NumLoci)*): ContigLengths =
    (for {
      (contig, length) <- contigs
    } yield
      contig -> NumLoci(length)
    )
    .toMap
}

object ContigLengthsUtil extends ContigLengthsUtil
