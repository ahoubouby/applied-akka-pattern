import sbt._

object Version {
  val akkaVer = "2.6.3"
  val logbackVer = "1.2.3"
  val scalaVer = "2.13.3"
  val scalaTestVer = "3.1.0"

}

object Dependencies {
  val dependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % Version.akkaVer,
    "com.typesafe.akka" %% "akka-stream" % Version.akkaVer,
    "com.typesafe.akka" %% "akka-persistence" % Version.akkaVer,
    "com.typesafe.akka" %% "akka-cluster" % Version.akkaVer,
    "com.typesafe.akka" %% "akka-slf4j" % Version.akkaVer,
    "ch.qos.logback" % "logback-classic" % Version.logbackVer,
    "com.typesafe.akka" %% "akka-testkit" % Version.akkaVer % "test",
    "org.scalatest" %% "scalatest" % Version.scalaTestVer % "test"
  )
}
