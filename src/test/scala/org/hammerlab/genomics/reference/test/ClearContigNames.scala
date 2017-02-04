package org.hammerlab.genomics.reference.test

import org.hammerlab.genomics.reference.ContigName
import org.hammerlab.test.Suite
import org.scalatest.BeforeAndAfterEach

/**
 * Mix-in that resets the contig-name cache in the default implicit
 * [[org.hammerlab.genomics.reference.ContigName.Strict]] instance after each test-case.
 */
trait ClearContigNames
  extends BeforeAndAfterEach {

  self: Suite ⇒

  before {
    ContigName.Strict.clear()
  }
}
