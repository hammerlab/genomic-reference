package org.hammerlab.genomics.reference

import org.hammerlab.iterator.SimpleBufferedIterator

import scala.collection.mutable

/**
 * Divide an iterator into a series of contig-restricted iterators.
 *
 * Takes an iterator of arbitrary elements and a function for extracting a [[ContigName]] from them.
 *
 * Emits [[(ContigName, ContigIterator)]] tuples.
 *
 * See companion object for public constructors.
 */
class ContigsIterator[T] private(it: BufferedIterator[T],
                                 contigNameFn: T => ContigName)
  extends SimpleBufferedIterator[(ContigName, ContigIterator[T])] {

  var contigRegions: ContigIterator[T] = _

  val seenContigs = mutable.Set[ContigName]()

  override def _advance: Option[(ContigName, ContigIterator[T])] = {
    // Discard any remaining reads from the previous partition.
    if (contigRegions != null) {
      while (contigRegions.hasNext) {
        contigRegions.next()
      }
    }

    if (!it.hasNext)
      None
    else {
      contigRegions = ContigIterator(it, contigNameFn)
      val contigName = contigRegions.contigName

      if (seenContigs(contigName))
        throw RepeatedContigException(
          s"Repeating $contigName after seeing contigs: ${seenContigs.mkString(",")}"
        )
      else
        seenContigs += contigName

      Some(contigNameFn(it.head) -> contigRegions)
    }
  }
}

/**
 * Public [[ContigsIterator]] constructors infer the [[org.hammerlab.genomics.reference.ContigName]]-function from a
 * [[HasContig]] or a [[(HasContig, T)]].
 */
object ContigsIterator {

  def apply[T <: HasContig](it: Iterator[T]): ContigsIterator[T] =
    new ContigsIterator(it.buffered, _.contigName)

  def byKey[C <: HasContig, T](it: Iterator[(C, T)]): ContigsIterator[(C, T)] =
    new ContigsIterator(it.buffered, _._1.contigName)
}

case class RepeatedContigException(msg: String) extends Exception(msg)