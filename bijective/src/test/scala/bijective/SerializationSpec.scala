package bijective

import org.scalactic.anyvals.{PosInt, PosZDouble, PosZInt}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, MustMatchers}
import util.ArbitraryBuilder

case class SimpleCaseClass(member1: Int, member2: Float, member3: String)
case class NestedCaseClass(member1: SimpleCaseClass, member2: SimpleCaseClass)

trait Polymorphic
case class PolymorphicA(member1: Int) extends Polymorphic
case class PolymorphicB(member1: Int, member2: String) extends Polymorphic


class SerializationSpec extends FlatSpec with MustMatchers with GeneratorDrivenPropertyChecks {

  implicit val propertyCheckConfiguration: PropertyCheckConfiguration = PropertyCheckConfiguration(
    minSuccessful = PosInt(1000),
    maxDiscardedFactor = PosZDouble(5.0),
    minSize = PosZInt(0),
    sizeRange = PosZInt(100),
    workers = PosInt(1)
  )


  implicit val arbitrarySimpleCaseClass = ArbitraryBuilder.build[SimpleCaseClass]
  implicit val arbitraryNestedCaseClass = ArbitraryBuilder.build[NestedCaseClass]


  "Serialization" must "be reversible for simple case classes" in {
    forAll{ simple: SimpleCaseClass  =>
      val serialized = Serialization.serialize(simple)
      val deserialized = Serialization.deserialize[SimpleCaseClass](serialized)

      deserialized must be(simple)
    }
  }

  it must "be reversible for nested case classes" in {
    forAll{ nested: NestedCaseClass  =>
      val serialized = Serialization.serialize(nested)
      val deserialized = Serialization.deserialize[NestedCaseClass](serialized)

      deserialized must be(nested)
    }
  }


}
