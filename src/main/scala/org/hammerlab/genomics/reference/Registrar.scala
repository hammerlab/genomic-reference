package org.hammerlab.genomics.reference

import com.esotericsoftware.kryo.Kryo
import org.apache.spark.serializer.KryoRegistrator
import org.hammerlab.genomics.reference.ContigName.{ Normalization, Factory }

abstract class RegistrarI(factory: Factory) extends KryoRegistrator {
  implicit val n = factory
  override def registerClasses(kryo: Kryo): Unit = {
    kryo.register(classOf[Locus])
    kryo.register(classOf[ContigName], new ContigNameSerializer)
    kryo.register(classOf[Position])
  }
}

/**
 * Expose two registrars, one that does "strict" contig-name-consistency-checking, and another that doesn't.
 *
 * In order for worker tasks to handle/create [[ContigName]]s consistently with the driver, the kryo serializers have to
 * behave consistently.
 */
class Registrar extends RegistrarI(Normalization.Strict)
class PermissiveRegistrar extends RegistrarI(Normalization.Lenient)

object PermissiveRegistrar extends PermissiveRegistrar
