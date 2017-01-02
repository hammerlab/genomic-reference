package org.hammerlab.genomics.reference.test

import org.hammerlab.test.implicits.{ convertKey, toList }
import org.hammerlab.genomics.reference.Locus

trait LocusUtil {
  // In tests, allow permissive interoperability between [[Locus]]s and [[Long]]s
  implicit def unpackLocus(locus: Locus): Long = locus.locus
  implicit def intToLocus(locus: Int): Locus = Locus(locus)

  implicit def toLocusKey[T] = convertKey[Int, T, Locus] _
  implicit def toLocusKeySeq[T] = {
    implicit val tlk = toLocusKey[T] _
    toList[(Int, T), (Locus, T)] _
  }

  implicit def rangeToList(range: Range): List[Locus] = range.toList.map(Locus(_))
  implicit def toLocusList = toList[Int, Locus] _
}

object LocusUtil extends LocusUtil
