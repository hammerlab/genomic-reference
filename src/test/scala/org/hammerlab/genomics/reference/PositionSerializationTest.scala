package org.hammerlab.genomics.reference

import org.hammerlab.genomics.reference.test.ClearContigNames
import org.hammerlab.spark.test.suite.{ KryoSparkSuite, SparkSerialization }
import org.hammerlab.genomics.reference.test.LociConversions._

class PositionSerializationTest
  extends KryoSparkSuite(classOf[Registrar])
    with SparkSerialization
    with ClearContigNames {

  test("Position/ContigName classes add no serde space-overhead") {
    serialize(Position("chr1", 123)).length should ===(7)

    serialize("chr1").length should ===(5)
    serialize(123).length should ===(3)

    serialize("chr1": ContigName).length should ===(5)

    serialize(("chr1", 123)).length should ===(9)
  }
}
