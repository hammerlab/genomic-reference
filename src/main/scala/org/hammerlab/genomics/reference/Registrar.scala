package org.hammerlab.genomics.reference

import com.esotericsoftware.kryo.Kryo
import org.apache.spark.serializer.KryoRegistrator

class Registrar extends KryoRegistrator {
  override def registerClasses(kryo: Kryo): Unit = {
    kryo.register(classOf[Locus])
    kryo.register(classOf[ContigName])
    kryo.register(classOf[Position])
  }
}
