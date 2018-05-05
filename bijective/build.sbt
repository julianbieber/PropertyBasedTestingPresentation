name := "bijective"
resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

libraryDependencies ++= LibraryDependencies.root ++ Seq(
  "beyondthelines" %% "pbdirect" % "0.1.0"
)