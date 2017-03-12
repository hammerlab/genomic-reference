package org.hammerlab.genomics.reference

import org.hammerlab.genomics.reference.test.{ ClearContigNames, LociConversions }
import org.hammerlab.spark.test.suite.{ KryoSparkSuite, SparkSerialization }

class PositionSerializationTest
  extends KryoSparkSuite(classOf[Registrar])
    with SparkSerialization
    with LociConversions
    with ClearContigNames {

  test("Position/ContigName classes add no serde space-overhead") {
    serialize(Position("chr1", 123)).length should ===(7)

    serialize("chr1").length should ===(5)
    serialize(123).length should ===(3)

    serialize("chr1": ContigName).length should ===(5)

    serialize(("chr1", 123)).length should ===(9)
  }
}
