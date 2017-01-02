package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.{ Interval, Locus }
import LocusUtil._

trait IntervalsUtil {
  def makeIntervals(intervals: Seq[(Int, Int, Int)]): BufferedIterator[Interval] =
    (for {
      (start, end, num) <- intervals
      i <- 0 until num
    } yield
      Interval(start, end)
    )
    .iterator
    .buffered

  def apply(start: Int, end: Int): Interval = Interval(start, end)
}
