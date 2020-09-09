import sbt.Keys._

object CommonSettings {
  import CompileOptions._

  lazy val commonSettings = Seq(
    organization := "com.ahoubouby.training",
    version := "1.0.0",
    scalaVersion := Version.scalaVer,
    scalacOptions ++= compileOptions,
    libraryDependencies ++= Dependencies.dependencies
  )
}
