package regression

import org.scalacheck.Gen
import org.scalactic.anyvals.{PosInt, PosZDouble, PosZInt}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, MustMatchers}

class FibonacciSpec extends FlatSpec with MustMatchers with GeneratorDrivenPropertyChecks {

  implicit val propertyCheckConfiguration: PropertyCheckConfiguration = PropertyCheckConfiguration(
    minSuccessful = PosInt(10),
    maxDiscardedFactor = PosZDouble(5.0),
    minSize = PosZInt(100),
    sizeRange = PosZInt(10),
    workers = PosInt(8)
  )

  "Fibonacci" must "produce implementation independent results for small numbers" in {

  }

  it must "not throw an exception for large inputs" in {

  }

}
