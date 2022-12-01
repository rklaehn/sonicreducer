import ReleaseTransformations._

lazy val sonicReducerSettings = Seq(
  organization := "com.rklaehn",
  scalaVersion := "3.2.1",
  crossScalaVersions := Seq( "2.13.10", "3.0.2", "3.1.3", "3.2.1"),
  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.2.14" % "test",
    "org.typelevel" %% "spire" % "0.18.0" % "test"
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature"
  ),
  licenses += ("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("http://github.com/rklaehn/sonicreducer")),

  // release stuff
  credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
  releaseCrossBuild := true,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishMavenStyle := true,
  Test / publishArtifact := false,
  pomIncludeRepository := Function.const(false),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.trim.endsWith("SNAPSHOT"))
      Some("Snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("Releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra :=
    <scm>
      <url>git@github.com:rklaehn/sonicreducer.git</url>
      <connection>scm:git:git@github.com:rklaehn/sonicreducer.git</connection>
    </scm>
    <developers>
      <developer>
        <id>r_k</id>
        <name>R&#xFC;diger Klaehn</name>
        <url>http://github.com/rklaehn/</url>
      </developer>
    </developers>
  ,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    ReleaseStep(action = Command.process("package", _)),
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    ReleaseStep(action = Command.process("publishSigned", _)),
    setNextVersion,
    commitNextVersion,
    ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
    pushChanges))

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false)

lazy val root = coreAggregate
  .settings(name := "root")
  .settings(sonicReducerSettings: _*)
  .settings(noPublish: _*)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("."))
  .settings(name := "sonicreducer")
  .settings(sonicReducerSettings: _*)

lazy val coreJVM = core.jvm
lazy val coreJS = core.js
lazy val coreAggregate =
  project.in(file("."))
    .aggregate(coreJVM, coreJS)

lazy val bench = (project in file("bench"))
  .settings(noPublish: _*)
  .settings(sonicReducerSettings: _*)
  .settings(name := "bench")
  .dependsOn(coreAggregate % "test -> test")
  .enablePlugins(JmhPlugin)
