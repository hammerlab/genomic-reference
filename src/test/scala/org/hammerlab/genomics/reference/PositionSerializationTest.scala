package org.hammerlab.genomics.reference

import org.hammerlab.genomics.reference.test.LocusUtil
import org.hammerlab.spark.test.suite.{ KryoSparkSuite, SparkSerialization }

class PositionSerializationTest
  extends KryoSparkSuite(classOf[Registrar])
    with SparkSerialization
    with LocusUtil {

  test("Position/ContigName classes add no serde space-overhead") {
    serialize(Position("chr1", 123)).length should ===(9)

    serialize("chr1").length should ===(5)
    serialize(123).length should ===(3)

    serialize("chr1": ContigName).length should ===(5)

    serialize(("chr1", 123)).length should ===(9)
  }
}
