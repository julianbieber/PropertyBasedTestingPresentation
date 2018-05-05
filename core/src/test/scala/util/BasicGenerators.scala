package util

import org.joda.time.{DateTime, DateTimeZone, Period}
import org.scalacheck.{Arbitrary, Gen}

object BasicGenerators {
  val defaultDateTime: DateTime = new DateTime(1999, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC)

  def genDateTime(
                   start: DateTime = defaultDateTime,
                   period: Period = Period.years(100)
                 ): Gen[DateTime] = {
    val startTimestamp = start.getMillis
    val endTimestamp = start.plus(period).getMillis
    Gen.choose(startTimestamp, endTimestamp).map{i => new DateTime(i, DateTimeZone.UTC)}
  }

  def genBigDecimal: Gen[BigDecimal] = Gen.posNum[Double].map(BigDecimal(_))

  def genGtin: Gen[Long] = Gen.choose[Long](10000000L, 99999999999999L)

  def genOption[T](gen: Gen[T]): Gen[Option[T]] = Gen.option(gen)

  def oneRandom[T](gen: Gen[T], retryCounter:Int = 0): T = {
    try {
      gen.sample.get
    } catch {
      case e: Throwable =>
        if (retryCounter < 10)
          oneRandom(gen, retryCounter + 1)
        else
          throw e
    }
  }

  def genBoolean: Gen[Boolean] = Gen.oneOf(true, false)

  def genMap[A, B](genKey: Gen[A], genValue: Gen[B]): Gen[Map[A, B]] = Gen.mapOf(genTuple(genKey, genValue))

  def genNonEmptyMap[A, B](genKey: Gen[A], genValue: Gen[B]): Gen[Map[A, B]] = Gen.nonEmptyMap(genTuple(genKey, genValue))

  def genTuple[A, B](genKey: Gen[A], genValue: Gen[B]): Gen[(A, B)] = for {
    key <- genKey
    value <- genValue
  } yield {
    (key, value)
  }

  def genSeq[A](gen: Gen[A], size: Option[Int] = None): Gen[Seq[A]] =
    size
      .map(Gen.listOfN(_, gen))
      .getOrElse(Gen.listOf(gen))
      .map(_.toSeq)

  def genNonEmptySeq[A](gen: Gen[A]): Gen[Seq[A]] = Gen.nonEmptyListOf(gen).map(_.toSeq)

  def genSet[A](gen: Gen[A], size: Option[Int] = None): Gen[Set[A]] =
    size
      .map(s => genSeq(gen, size).map(_.toSet).suchThat(_.size==s))
      .getOrElse(Gen.containerOf[Set, A](gen))

  def genNonEmptySet[A](gen: Gen[A]): Gen[Set[A]] = genSet(gen).suchThat(_.nonEmpty)

  def genUUID: Gen[String] = Gen.uuid.map(_.toString)

  def genString: Gen[String] = genSeq(genChar).map(_.mkString)

  def genNonEmptyAlphaString: Gen[String] = Gen.nonEmptyListOf(Gen.alphaChar).map(_.mkString)

  def genChar: Gen[Char] = Arbitrary.arbChar.arbitrary

  def genNonEmptyString: Gen[String] = Gen.nonEmptyListOf(genChar).map(_.mkString)

}
