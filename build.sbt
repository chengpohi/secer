name := "seccrawler"

version := "1.0"

scalaVersion := "2.11.1"

unmanagedBase := baseDirectory.value / "lib"

libraryDependencies ++= Seq (
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.apache.httpcomponents" % "httpclient" % "4.3",
  "org.slf4j" % "slf4j-simple" % "1.7.7",
  "com.typesafe" % "config" % "1.2.1",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1" % "test",
  "org.jsoup" % "jsoup" % "1.7.2",
  "com.typesafe.akka" %% "akka-actor" % "2.3.8"
)
