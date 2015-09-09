name := """play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .aggregate(application, domain, infrastructure)
  .settings(
    run := {
      (run in application in Compile).evaluated
    }
  )

lazy val application = (project in file("application"))
  .dependsOn(domain, infrastructure)
  .enablePlugins(PlayScala).settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      jdbc,
      cache,
      ws,
      evolutions
    ),
    // Play provides two styles of routers, one expects its actions to be injected, the
    // other, legacy style, accesses its actions statically.
    routesGenerator := InjectedRoutesGenerator
  )

lazy val domain = (project in file("domain"))
  .dependsOn(infrastructure).settings(commonSettings: _*)
  .settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "anorm" % "2.4.0"
    ),
    parallelExecution in Test := false
  )

lazy val infrastructure = (project in file("infrastructure"))
  .settings(commonSettings: _*).settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "anorm" % "2.4.0",
      "com.typesafe.play" %% "play-slick" % "1.0.1",
      "mysql" % "mysql-connector-java" % "5.1.34"
    ),
    parallelExecution in Test := false
  )

lazy val commonSettings = Seq(
  scalaVersion := "2.11.6",
  libraryDependencies ++= Seq(
    jdbc,
    cache,
    ws,
    specs2 % Test
  )
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"