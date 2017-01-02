package org.hammerlab.genomics.reference

import scala.collection.Iterator

trait Interval extends Any {

  /** 0-based start position on a contig. */
  def start: Locus

  /** 0-based end position on a contig. */
  def end: Locus

  def contains(locus: Locus): Boolean = start <= locus && locus < end

  /** Iterate through elements in the range. */
  def iterator(): Iterator[Locus] =
    new Iterator[Locus] {
      private var i = start

      override def hasNext: Boolean = i < end

      override def next(): Locus =
        if (hasNext) {
          val result = i
          i = i.next
          result
        } else
          Iterator.empty.next()
    }

  /** Number of elements in the range. */
  def length: NumLoci = end - start

  override def toString: String = s"$start-$end"
}

object Interval {
  def apply(start: Locus, end: Locus): Interval = IntervalImpl((start, end))

  def apply(t: (Locus, Locus)): Interval = IntervalImpl(t)

  def unapply(i: Interval): Option[(Locus, Locus)] = Some((i.start, i.end))

  def orderByStart[I <: Interval] = Ordering.by[I, Locus](_.start)
  def orderByEndDesc[I <: Interval] = Ordering.by[I, Locus](_.end).reverse
}

private case class IntervalImpl(t: (Locus, Locus))
  extends AnyVal
    with Interval {
  override def start: Locus = t._1
  override def end: Locus = t._2
}
