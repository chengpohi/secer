name := "secer"

enablePlugins(JmhPlugin)


import Settings._

lazy val indexer = project.in(file("modules/indexer"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)

lazy val parser = project.in(file("modules/parser"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(indexer)
  .dependsOn(indexer)

lazy val fetcher = project.in(file("modules/fetcher"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(parser)
  .dependsOn(parser)

lazy val client = project.in(file("modules/transport-client"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(root)
  .dependsOn(root)

lazy val root = project.in(file("app"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(fetcher)
  .dependsOn(fetcher)


lazy val fb = project.in(file("plugins/fb"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)


lazy val appstore = project.in(file("plugins/as"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(client)
  .dependsOn(client)

lazy val so = project.in(file("plugins/so"))
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonDependencies ++ akkaDependencies)
  .aggregate(client)
  .dependsOn(client)

