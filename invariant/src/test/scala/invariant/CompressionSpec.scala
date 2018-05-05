package invariant

import org.scalactic.anyvals.{PosInt, PosZDouble, PosZInt}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, MustMatchers}
import org.scalacheck.{Gen, Shrink}

class CompressionSpec extends FlatSpec with MustMatchers with GeneratorDrivenPropertyChecks {

  implicit val propertyCheckConfiguration: PropertyCheckConfiguration = PropertyCheckConfiguration(
    minSuccessful = PosInt(1000),
    maxDiscardedFactor = PosZDouble(5.0),
    minSize = PosZInt(1000),
    sizeRange = PosZInt(1000),
    workers = PosInt(1)
  )

  val genPositiveByteArray: Gen[Array[Byte]] = Gen.containerOf[Array, Byte](Gen.choose[Byte](64, 127))

  implicit val noShrink: Shrink[Byte] = Shrink.shrinkAny

  "Compression" must "be lossless" in {
    forAll(genPositiveByteArray) { uncompressed =>
      whenever(uncompressed.length > 1) {
        val compressed = Compression.compress(uncompressed)
        Compression.decompress(compressed) must be(uncompressed)
      }
    }
  }

  it must "compress the data" in {
    forAll(genPositiveByteArray) { uncompressed =>
      whenever(uncompressed.length > 1000) {
        val compressed = Compression.compress(uncompressed)
        compressed.length must be <= uncompressed.length
      }
    }
  }

}
