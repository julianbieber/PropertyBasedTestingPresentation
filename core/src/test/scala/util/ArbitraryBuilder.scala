package util

import org.scalacheck.Arbitrary

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

/**
  * Created by Andreas Frankenberger on 24/10/17.
  */
object ArbitraryBuilder {


  def build[A]: Arbitrary[A] = macro build_impl[A]

  def build_impl[A](c: Context)(implicit A: c.WeakTypeTag[A]) = {
    import c.universe._

    val constructors = A.tpe.decls.filter(_.isConstructor)

    if (constructors.size > 1) {
      c.abort(c.enclosingPosition, s"Found more than one constructor for class ${A.tpe}")
    }else if (constructors.isEmpty){
      c.abort(c.enclosingPosition, s"Class ${A.tpe} has no constructor")
    }else{
      val onlyConstructor = constructors.head

      onlyConstructor.asMethod.paramLists match {
        case Seq(Seq())=>
          c.Expr(q"org.scalacheck.Arbitrary(Gen.const(new $A()))")
        case Seq(parameterGroup) =>
          val args = parameterGroup.map(_.info).zipWithIndex.map { case (tpe, i) => tpe -> TermName("p" + i) }

          val forHead = args.map { case (tpe, parameterName) => fq"$parameterName <- org.scalacheck.Arbitrary.arbitrary[$tpe]" }

          val constructorParameters = args.map { case (tpe, i) => q"$i" }

          c.Expr(q"org.scalacheck.Arbitrary(for(..$forHead) yield new $A(..$constructorParameters))")
        case _=>
          c.abort(c.enclosingPosition, s"Constructor of class ${A.tpe} has more than one parameter groups")
      }
    }
  }
}
