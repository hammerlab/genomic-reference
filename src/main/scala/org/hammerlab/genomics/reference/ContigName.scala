package org.hammerlab.genomics.reference

import scala.collection.mutable

/**
 * Implicit-value-class wrapper for strings representing names of contigs.
 *
 * Construction is controlled by the companion object, which uses an implicit
 * [[org.hammerlab.genomics.reference.ContigName.Factory]] to decide whether to allow+collapse observed contig-names
 * like "chr1" and "1" or throw an
 * [[org.hammerlab.genomics.reference.ContigName.InconsistentContigNamesException]]; default behavior is the
 * latter, but permissive handling can be enabled by importing [[org.hammerlab.genomics.reference.ContigName.LenientInterface]].
 *
 * Wrapped strings are interned.
 */
class ContigName private(val name: String)
  extends AnyVal
    with Serializable
    with Ordered[ContigName] {

  import ContigName.notFound

  // Sort order: chr1, chr2, …, chr22, chrX, chrY, <other>.
  override def compare(that: ContigName): Int =
    if (getContigRank == notFound && that.getContigRank == notFound)
      name.compare(that.name)
    else
      getContigRank - that.getContigRank

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

  private[reference] val names = mutable.Map[String, ContigName]()

  // Map from string contig name to ordered rank.
  private[reference] val map: Map[String, Int] =
    ((1 to 22).map(_.toString) ++ List("X", "Y", "MT"))
      .zipWithIndex
      .toMap

  private val notFound = map.size

  def apply(name: String)(implicit factory: Factory): ContigName = name

  object Normalization {
    // Import this in order to allow "permissive" contig-name creation/handling: "chr1" and "1" will be normalized to
    // just "1".
    implicit object Lenient extends LenientInterface
    implicit val Strict = ContigName.Strict
  }

  sealed trait Factory
    extends Serializable
      with Function1[String, ContigName] {

    override def apply(name: String): ContigName = {
      val normalizedName = normalize(name).intern()
      names.getOrElseUpdate(
        normalizedName,
        new ContigName(name.intern())
      )
    }

    def normalize(name: String): String
  }

  // Strict ContigName-validation is the default.
  implicit object Strict extends Factory {
    def clear(): Unit = {
      seenChrsOpt = None
      ContigName.names.clear()
    }

    /**
     * True iff a contig-name ∈ {1-22,X,Y} with "chr" prefix has been observed.
     * False iff a contig-name ∈ {1-22,X,Y} without "chr" prefix has been observed.
     */
    var seenChrsOpt: Option[Boolean] = None

    override def normalize(name: String): String =
      if (name.startsWith("chr") && map.contains(name.drop(3))) {
        if (seenChrsOpt.contains(false))
          throw InconsistentContigNamesException(name)
        seenChrsOpt = Some(true)
        name
      } else if (map.contains(name)) {
        if (seenChrsOpt.contains(true))
          throw InconsistentContigNamesException(name)
        seenChrsOpt = Some(false)
        name
      } else
        name

    override def toString(): String = "Strict"
  }

  trait LenientInterface extends Factory {
    override def normalize(name: String): String =
      if (name.startsWith("chr") && map.contains(name.drop(3)))
        name.drop(3)
      else
        name

    override def toString(): String = "Lenient"
  }

  case class InconsistentContigNamesException(name: String)
    extends Exception(
      s"Contig name $name is inconsistent with previously-observed contigs: ${ContigName.names.keys.toVector.sorted.mkString(",")}"
    )
}
