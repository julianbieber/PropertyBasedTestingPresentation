name := "PropertyBasedTestsPresentation"

resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

version := "0.1"

scalaVersion := "2.12.6"


lazy val core = project.in(file("core"))

lazy val bijective = project.in(file("bijective")).dependsOn(`core` % "test->test;compile->compile")

lazy val invariant = project.in(file("invariant")).dependsOn(`core` % "test->test;compile->compile")

lazy val regression = project.in(file("regression")).dependsOn(`core` % "test->test; compile->compile")

lazy val root = project.in(file("."))
  .aggregate(`core`)
  .aggregate(`bijective`)
  .aggregate(`invariant`)
  .aggregate(`regression`)