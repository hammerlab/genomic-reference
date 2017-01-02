package org.hammerlab.genomics

import org.hammerlab.genomics.bases.Bases

package object reference {

  private val intOrdering = implicitly[Ordering[Int]]

  implicit class WindowSize(val size: Int)
    extends AnyVal
      with Ordered[Int] {

    override def toString: String = size.toString

    override def compare(that: Int): Int = intOrdering.compare(size, that)
    def ==(that: Int) = size == that
  }

  object WindowSize {
    implicit def unpackWindowSize(window: WindowSize): Int = window.size
  }

  type KmerLength = Int

  implicit class ContigName(val name: String)
    extends AnyVal
      with Serializable
      with Ordered[ContigName] {

    import ContigName.notFound

    // Sort order: chr1, chr2, …, chr22, chrX, chrY, <other>.
    override def compare(that: ContigName): Int = {
      if (getContigRank == notFound && that.getContigRank == notFound)
        name.compare(that.name)
      else
        getContigRank - that.getContigRank
    }

    private def normalizedName: String =
      if (name.startsWith("chr"))
        name.drop(3)
      else
        name

    private def getContigRank: Int =
      ContigName.map
        .getOrElse(
          normalizedName,
          notFound
        )

    override def toString: String = name
  }

  /**
   * Helpers for dealing with contig-name strings.
   */
  object ContigName {

    // Map from string contig name to ordered rank.
    private val map: Map[String, Int] = ((1 to 22).map(_.toString) ++ List("X", "Y")).zipWithIndex.toMap

    private val notFound = map.size
  }

  private type LociT = Long

  private val lociOrdering = implicitly[Ordering[LociT]]

  implicit class Locus(val locus: LociT)
    extends AnyVal
      with Ordered[Locus]
      with Comparable[Locus] {

    def num = locus
    
    override def compare(that: Locus): Int =
      lociOrdering.compare(locus, that.locus)

    def prev: Locus = locus - 1
    def next: Locus = new Locus(locus + 1)

    def +(other: Locus): Locus = new Locus(locus + other.locus)
    def -(other: Locus): Locus = math.max(0, locus - other.locus)

    def -(n: Int): Locus = Locus(math.max(0, locus - n))
    def +(n: Int): Locus = Locus(locus + n)

    def min(other: Locus): Locus = Locus(locus min other.locus)
    def max(other: Locus): Locus = Locus(locus max other.locus)

    override def toString: String = locus.toString
  }

  type NumLoci = Locus

  object NumLoci {
    def apply(locus: Long): NumLoci = new Locus(locus)
    def apply(locus: Int): NumLoci = new Locus(locus)
  }

  implicit object Locus extends Numeric[Locus] {

    implicit def makeLocus(locus: LociT): Locus = Locus(locus)

    def apply(basesLength: Bases#LengthT): Locus = new Locus(basesLength.size)
    def apply(locus: Locus): Locus = new Locus(locus.locus)

    implicit def unwrapLocus(locus: Locus): LociT = locus.locus

    def plus(x: Locus, y: Locus): Locus = x + y
    def minus(x: Locus, y: Locus): Locus = x - y
    def times(x: Locus, y: Locus): Locus = ???
    def quot(x: Locus, y: Locus): Locus = ???
    def rem(x: Locus, y: Locus): Locus = ???
    def negate(x: Locus): Locus = ???
    def fromInt(x: Int): Locus = new Locus(x)
    def toInt(x: Locus): Int = x.toInt
    def toLocus(x: Locus): Locus = x
    def toFloat(x: Locus): Float = x.toFloat
    def toDouble(x: Locus): Double = x.toDouble
    override def toLong(x: Locus): LociT = x
    override def compare(x: Locus, y: Locus): Int = x.compare(y)
  }

  implicit class ContigLengths(val lengths: Map[ContigName, NumLoci]) extends AnyVal {
    def totalLength: NumLoci = lengths.values.sum
  }

  object ContigLengths {
    val empty: ContigLengths = Map[ContigName, NumLoci]()
    implicit def unwrapContigLengths(contigLengths: ContigLengths): Map[ContigName, NumLoci] = contigLengths.lengths
  }
}
