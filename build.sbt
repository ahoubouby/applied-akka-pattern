import sbt._

lazy val base = (project in file("."))
  .settings(CommonSettings.commonSettings: _*)
  .enablePlugins(DockerPlugin)
