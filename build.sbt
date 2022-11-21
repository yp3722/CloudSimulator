ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.3"

val logbackVersion = "1.3.0-alpha10"
val sfl4sVersion = "2.0.0-alpha5"
val typesafeConfigVersion = "1.4.1"
val apacheCommonIOVersion = "2.11.0"
val scalacticVersion = "3.2.9"
val generexVersion = "1.0.2"
val cloudSimVerion = "7.3.0"

lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin,JavaAppPackaging)
  .settings(
    name := "cloudSimulator"
  )

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-core" % logbackVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.slf4j" % "slf4j-api" % sfl4sVersion
)

libraryDependencies += "org.scalactic" %% "scalactic" % scalacticVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalacticVersion % Test
libraryDependencies += "org.scalatest" %% "scalatest-featurespec" % scalacticVersion % Test
libraryDependencies += "com.typesafe" % "config" % typesafeConfigVersion

libraryDependencies += "org.cloudsimplus" % "cloudsim-plus" % cloudSimVerion
dockerBaseImage := "openjdk:17"
