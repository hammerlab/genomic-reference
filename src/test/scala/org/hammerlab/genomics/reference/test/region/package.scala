package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.{ ContigName, Locus, Region }
import org.hammerlab.genomics.reference.test.LociConversions._
import org.hammerlab.test.Cmp

package object region {
  implicit def makeRegion(t: (String, Int, Int)): Region = Region(t._1, t._2, t._3)

  implicit def makeRegionsWithDepthsSeq(reads: Seq[(String, Int, Int, Int)]): Seq[Region] =
    makeRegionsWithDepths(reads).toSeq

  implicit def makeRegionsWithDepths(reads: Seq[(String, Int, Int, Int)]): BufferedIterator[Region] =
    (for {
      (contig, start, end, num) ← reads
      i ← 0 until num
    } yield
      Region(contig, start, end)
    )
    .iterator
    .buffered

  implicit def makeRegions(reads: Seq[(String, Int, Int)]): List[Region] =
    for {
      (contig, start, end) ← reads.toList
    } yield
      Region(contig, start, end)

  implicit def makeRegionsIterator(reads: Seq[(String, Int, Int)]): Iterator[Region] =
    makeRegions(reads).iterator

  def Regions(reads: (String, Int, Int)*): List[Region] = makeRegions(reads)

  implicit def cmpContigName(implicit cmp: Cmp[String]): Cmp.Aux[ContigName, cmp.Diff] = Cmp.by[String, ContigName](_.name)(cmp)
  implicit def cmpLocus(implicit cmp: Cmp[Long]): Cmp.Aux[Locus, cmp.Diff] = Cmp.by[Long, Locus](_.locus)(cmp)

  implicit def cmpRegion(implicit cmp: Cmp[(String, Long, Long)]): Cmp.Aux[Region, cmp.Diff] =
    Cmp.by[
      (String, Long, Long),
      Region
    ] {
      case Region(contigName, start, end) ⇒
        (
          contigName.name,
          start.locus,
          end.locus
        )
    } (cmp)
}
