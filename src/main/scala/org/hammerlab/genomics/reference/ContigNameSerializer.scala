package org.hammerlab.genomics.reference

import com.esotericsoftware.kryo.io.{ Input, Output }
import com.esotericsoftware.kryo.{ Kryo, Serializer }
import org.hammerlab.genomics.reference.ContigName.Factory

class ContigNameSerializer()(implicit factory: Factory) extends Serializer[ContigName] {
  override def write(kryo: Kryo, output: Output, contigName: ContigName): Unit = output.writeString(contigName.name)
  override def read(kryo: Kryo, input: Input, `type`: Class[ContigName]): ContigName = input.readString()
}
