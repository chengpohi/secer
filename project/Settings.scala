import sbt._
import sbt.Keys._

object Settings {
  lazy val commonSettings = Seq(
    version := "0.1",
    scalaVersion := "2.12.1",
    resolvers += Resolver.mavenLocal,
    ivyScala := ivyScala.value map {
      _.copy(overrideScalaVersion = true)
    }
  )


  lazy val akkaDependencies = Seq(
    "com.typesafe" % "config" % "1.2.1",
    "com.typesafe.akka" %% "akka-actor" % "2.4.16",
    "com.typesafe.akka" %% "akka-testkit" % "2.4.16",
    "com.typesafe.akka" %% "akka-remote" % "2.4.16",
    "com.typesafe.akka" %% "akka-slf4j" % "2.4.16",
    "com.typesafe.akka" %% "akka-http-core" % "10.0.1",
    "com.typesafe.akka" %% "akka-http" % "10.0.1"
  )


  lazy val commonDependencies = Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.apache.logging.log4j" % "log4j-1.2-api" % "2.7",
    "org.apache.logging.log4j" % "log4j-api" % "2.7",
    "org.apache.logging.log4j" % "log4j-core" % "2.7",
    "org.jsoup" % "jsoup" % "1.8.3",
    "org.json4s" %% "json4s-native" % "3.5.0",
    "org.json4s" %% "json4s-jackson" % "3.5.0",
    "com.github.chengpohi" %% "hdsl" % "0.1-SNAPSHOT",
    "com.github.chengpohi" %% "elasticdsl" % "0.2.3-SNAPSHOT",
    "org.seleniumhq.selenium" % "selenium-java" % "2.35.0"
  )

}