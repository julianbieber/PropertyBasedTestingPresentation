package bijective

import pbdirect._

object Serialization {
  def serialize[A](any: A)(implicit writer: PBWriter[A]): Array[Byte] = {
    any.toPB
  }

  def deserialize[A](bytes: Array[Byte])(implicit reader: PBParser[A]): A = {
    bytes.pbTo[A]
  }
}
