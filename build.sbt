
val scalaV = "2.11.7"

lazy val commonSettings = Seq(
  scalaVersion := scalaV,
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaV)

lazy val macros = project.in(file("macros")).settings(commonSettings:_*)

lazy val root = project.in(file(".")).dependsOn(macros).settings(commonSettings:_*)