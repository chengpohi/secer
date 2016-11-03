
name := "secer"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.11.8",
  initialCommands in console := "import scalaz._, Scalaz._",
  resolvers += Resolver.mavenLocal,
  ivyScala := ivyScala.value map {
    _.copy(overrideScalaVersion = true)
  }
)



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
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.jsoup" % "jsoup" % "1.8.3",
  "com.github.chengpohi" % "hdsl_2.11" % "0.1-SNAPSHOT",
  "org.json4s" %% "json4s-native" % "3.2.10",
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "com.github.chengpohi" %% "elasticshell" % "0.2-SNAPSHOT",
  "com.lihaoyi" %% "fastparse" % "0.3.4",
  "org.scalactic" %% "scalactic" % "3.0.0",
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

lazy val client = project.in(file("transport-client"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(root)
  .dependsOn(root)

lazy val appstore = project.in(file("as"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(client)
  .dependsOn(client)
