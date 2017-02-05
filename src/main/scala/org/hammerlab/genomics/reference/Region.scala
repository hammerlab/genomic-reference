package org.hammerlab.genomics.reference

import com.esotericsoftware.kryo.Kryo

/**
 * Trait for objects that are associated with an interval on a genomic contig.
 */
trait Region
  extends Any
    with HasContig
    with Interval {

  /**
   * Does the region overlap the given locus, with halfWindowSize padding?
   */
  def overlapsLocus(locus: Locus, halfWindowSize: WindowSize = 0): Boolean =
     start - halfWindowSize <= locus && locus < end + halfWindowSize

  /**
   * Does the region overlap another reference region
   *
   * @param other another region on the genome
   * @return True if the the regions overlap
   */
  def overlaps(other: Region): Boolean =
    other.contigName == contigName &&
      (overlapsLocus(other.start) || other.overlapsLocus(start))

  override def toString: String = s"$contigName:$start-$end"
}

object Region {
  implicit def intraContigPartialOrdering[R <: Region] =
    new PartialOrdering[R] {
      override def tryCompare(x: R, y: R): Option[Int] = {
        if (x.contigName == y.contigName)
          Some(x.start.compare(y.start))
        else
          None
      }

      override def lteq(x: R, y: R): Boolean = {
        x.contigName == y.contigName && x.start <= y.start
      }
    }

  def apply(contigName: ContigName, start: Locus, end: Locus): Region =
    RegionImpl(contigName, start, end)

  def apply(contigName: ContigName, interval: Interval): Region =
    RegionImpl(contigName, interval.start, interval.end)

  def unapply(region: Region): Option[(ContigName, Locus, Locus)] =
    Some(
      region.contigName,
      region.start,
      region.end
    )

  def register(kryo: Kryo): Unit = {
    kryo.register(classOf[RegionImpl])
  }
}

private case class RegionImpl(t: (ContigName, Locus, Locus))
  extends AnyVal
    with Region {
  override def contigName: ContigName = t._1
  override def start: Locus = t._2
  override def end: Locus = t._3
}
