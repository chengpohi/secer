name := "secer"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.11.8",
  initialCommands in console := "import scalaz._, Scalaz._",
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
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
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1" % "test",
  "org.apache.httpcomponents" % "httpclient" % "4.3",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.jsoup" % "jsoup" % "1.8.3",
  "org.json4s" %% "json4s-native" % "3.2.10",
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "com.github.chengpohi" %% "elasticshell" % "1.1",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.2",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.4.2",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.2",
  "com.chuusai" %% "shapeless" % "2.3.1",
  "com.lihaoyi" %% "fastparse" % "0.3.4",
  "org.seleniumhq.selenium" % "selenium-java" % "2.35.0"
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

lazy val fb = project.in(file("fb"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)


lazy val root = project.in(file("app"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(fetcher)
  .dependsOn(fetcher)

