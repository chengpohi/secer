name := "secer"

version := "1.0"

scalaVersion := "2.11.7"

unmanagedBase := baseDirectory.value / "lib"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

libraryDependencies ++= Seq(
  "com.secer.elastic" % "elasticservice_2.11" % "1.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.apache.httpcomponents" % "httpclient" % "4.3",
  "org.slf4j" % "slf4j-simple" % "1.7.7",
  "com.typesafe" % "config" % "1.2.1",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1" % "test",
  "org.jsoup" % "jsoup" % "1.8.3",
  "com.typesafe.akka" %% "akka-actor" % "2.3.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.8",
  "com.typesafe.akka" %% "akka-remote" % "2.3.8",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.4.2",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.2",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.2",
  "org.siny.web" % "siny_2.11" % "1.0",
  "org.json4s" %% "json4s-native" % "3.2.10",
  "org.json4s" %% "json4s-jackson" % "3.2.10"
)

resolvers ++= Seq(
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)
