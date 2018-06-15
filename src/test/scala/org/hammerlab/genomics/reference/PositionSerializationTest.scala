package org.hammerlab.genomics.reference

import org.hammerlab.genomics.reference.test.{ ClearContigNames, LociConversions }
import org.hammerlab.spark.test.suite.{ KryoSparkSuite, SparkSerialization }

class PositionSerializationTest
  extends KryoSparkSuite
    with SparkSerialization
    with LociConversions
    with ClearContigNames {

  registrar[Registrar]

  test("Position/ContigName classes add no serde space-overhead") {
    ==(serialize(Position("chr1", 123)).length, 7)

    ==(serialize("chr1").length, 5)
    ==(serialize(123).length, 3)

    ==(serialize("chr1": ContigName).length, 5)

    ==(serialize(("chr1", 123)).length, 9)
  }
}
