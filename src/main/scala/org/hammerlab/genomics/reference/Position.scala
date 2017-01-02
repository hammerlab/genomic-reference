package org.hammerlab.genomics.reference

import com.esotericsoftware.kryo.Kryo

/**
 * Base trait for classes that logically exist at one genomic locus.
 */
case class Position(contigName: ContigName, locus: Locus)
  extends Region
    with HasLocus {

  def start = locus
  def end = locus.next

  override def toString: String = s"$contigName:$locus"
}

object Position {

  def registerKryo(kryo: Kryo): Unit = {
    kryo.register(classOf[Position])
    kryo.register(classOf[Array[Position]])
  }

  private implicit val tupleOrdering: Ordering[(ContigName, Locus)] = Ordering.Tuple2[ContigName, Locus]

  val totalOrdering: Ordering[Position] =
    new Ordering[Position] {
      override def compare(x: Position, y: Position): Int =
        tupleOrdering.compare(
          (x.contigName, x.locus),
          (y.contigName, y.locus)
        )
    }

  val partialOrdering =
    new PartialOrdering[Position] {
      override def tryCompare(x: Position, y: Position): Option[KmerLength] =
        if (x.contigName == y.contigName)
          Some(x.compare(y.locus))
        else
          None
      override def lteq(x: Position, y: Position): Boolean =
        x.contigName == y.contigName && x.locus <= y.locus
    }
}
