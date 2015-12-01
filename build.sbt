import ReleaseTransformations._

lazy val sonicReducerSettings = Seq(
  organization := "com.rklaehn",
  scalaVersion := "2.11.7",
  crossScalaVersions := Seq("2.10.5", "2.11.7"),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
    "org.scalatest" %%% "scalatest" % "3.0.0-M7" % "test"
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature"
  ),
  licenses += ("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("http://github.com/rklaehn/sonicreducer")),

  // release stuff
  releaseCrossBuild := true,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := Function.const(false),
  publishTo <<= (version).apply { v =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT"))
      Some("Snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("Releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := (
    <scm>
      <url>git@github.com:rklaehn/sonicreducer.git</url>
      <connection>scm:git:git@github.com:rklaehn/sonicreducer.git</connection>
    </scm>
    <developers>
      <developer>
        <id>r_k</id>
        <name>R&uuml;diger Klaehn</name>
        <url>http://github.com/rklaehn/</url>
      </developer>
    </developers>
  ),
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

lazy val root = project.in(file("."))
  .aggregate(sonicReducerJVM, sonicReducerJS)
  .settings(name := "sonicreducer-root")
  .settings(sonicReducerSettings: _*)
  .settings(noPublish: _*)

lazy val sonicReducer = crossProject.crossType(CrossType.Pure).in(file("."))
  .settings(name := "sonicreducer")
  .settings(sonicReducerSettings: _*)

lazy val sonicReducerJVM = sonicReducer.jvm
lazy val sonicReducerJS = sonicReducer.js
