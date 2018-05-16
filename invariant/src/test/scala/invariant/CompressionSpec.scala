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
  
  implicit val noShrink: Shrink[Byte] = Shrink.shrinkAny

  "Compression" must "be lossless" in {

  }

  it must "compress the data" in {

  }

}
