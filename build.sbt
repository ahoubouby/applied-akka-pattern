import sbt._

lazy val base = (project in file("."))
  .settings(CommonSettings.commonSettings: _*)
lazy val common = project.settings(CommonSettings.commonSettings: _*)
