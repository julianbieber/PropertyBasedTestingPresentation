import sbt._

object LibraryDependencies {
  val root = Seq(
    "org.scalacheck" %% "scalacheck" % "1.13.5" % Test,
    "org.scalatest" %% "scalatest" % "3.0.1" % Test
  )
}
