package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.hammerlab.test.implicits.{ toArray, toVector }

trait ContigNameUtil {
  implicit def makeContigs = toVector[String, ContigName] _
  implicit def makeContigsArray = toArray[String, ContigName] _
}

object ContigNameUtil extends ContigNameUtil
