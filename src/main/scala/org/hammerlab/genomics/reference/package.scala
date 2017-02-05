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

  private type LociT = Long

  private val lociOrdering = implicitly[Ordering[LociT]]

  case class Locus(locus: LociT)
    extends Ordered[Locus]
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
