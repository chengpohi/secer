name := "secer"

lazy val commonSettings = Seq(
  version := "1.0",
  scalaVersion := "2.11.7"
)

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

lazy val akkaDependencies = Seq(
  "com.typesafe" % "config" % "1.2.1",
  "com.typesafe.akka" %% "akka-actor" % "2.4.1",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.1",
  "com.typesafe.akka" %% "akka-remote" % "2.4.1",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.1"
)


lazy val commonDependencies = Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1" % "test",
  "org.apache.httpcomponents" % "httpclient" % "4.3",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.jsoup" % "jsoup" % "1.8.3",
  "org.json4s" %% "json4s-native" % "3.2.10",
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "com.github.chengpohi" %% "elasticshell" % "1.1",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.2",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.4.2",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.2"
)


lazy val indexer = project.in(file("indexer"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)

lazy val parser = project.in(file("parser"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(indexer)
  .dependsOn(indexer)

lazy val fetcher = project.in(file("fetcher"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(parser)
  .dependsOn(parser)

lazy val root = project.in(file("app"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(fetcher)
  .dependsOn(fetcher)

