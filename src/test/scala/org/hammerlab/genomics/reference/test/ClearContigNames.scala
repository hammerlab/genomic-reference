package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.scalatest.{ BeforeAndAfterEach, Suite }

/**
 * Mix-in that resets the contig-name cache in the default implicit
 * [[org.hammerlab.genomics.reference.ContigName.Strict]] instance after each test-case.
 */
trait ClearContigNames
  extends BeforeAndAfterEach {

  self: Suite ⇒

  abstract override def beforeEach(): Unit = {
    super.beforeEach()
    ContigName.Strict.clear()
  }
}
