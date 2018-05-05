package invariant

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.zip.{GZIPInputStream, GZIPOutputStream}

import scala.io.Source

object Compression {

  def compress(uncompressed: Array[Byte]): Array[Byte] = {
    val arrayOutputStream = new ByteArrayOutputStream()
    val zipOutputStream = new GZIPOutputStream(arrayOutputStream)
    try {
      zipOutputStream.write(uncompressed)
      zipOutputStream.close()
      arrayOutputStream.toByteArray
    } finally {
      arrayOutputStream.close()
    }
  }

  def decompress(compressed: Array[Byte]): Array[Byte] = {
    val arrayInputStream = new ByteArrayInputStream(compressed)
    val zipInputStream = new GZIPInputStream(arrayInputStream)
    try {
      Source.fromInputStream(zipInputStream).getLines().flatMap(_.toCharArray).toArray.map(_.toByte)
    } catch {
      case e: Throwable => e.printStackTrace(); throw e
    } finally {
      zipInputStream.close()
      arrayInputStream.close()
    }
  }

}
