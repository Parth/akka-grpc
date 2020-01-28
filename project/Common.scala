import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin
import akka.grpc.Dependencies.Versions.{ scala212, scala213 }
import org.scalafmt.sbt.ScalafmtPlugin.autoImport.scalafmtOnCompile

object Common extends AutoPlugin {
  override def trigger = allRequirements

  override def requires = JvmPlugin

  override def globalSettings =
    Seq(
      organization := "com.lightbend.akka.grpc",
      organizationName := "Lightbend Inc.",
      organizationHomepage := Some(url("https://www.lightbend.com/")),
      //    apiURL := Some(url(s"https://doc.akka.io/api/akka-grpc/${version.value}")),
      homepage := Some(url("https://akka.io/")),
      scmInfo := Some(ScmInfo(url("https://github.com/akka/akka-grpc"), "git@github.com:akka/akka-grpc")),
      developers += Developer(
          "contributors",
          "Contributors",
          "https://gitter.im/akka/dev",
          url("https://github.com/akka/akka-grpc/graphs/contributors")),
      licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
      description := "Akka gRPC - Support for building streaming gRPC servers and clients on top of Akka Streams.")

  val silencerVersion = "1.4.4"
  override lazy val projectSettings = Seq(
    scalacOptions ++= List(
        "-unchecked",
        "-deprecation",
        "-language:_",
        "-Xfatal-warnings",
        "-encoding",
        "UTF-8",
        "-P:silencer:globalFilters=Marked as deprecated in proto file"),
    javacOptions ++= List("-Xlint:unchecked", "-Xlint:deprecation"),
    libraryDependencies ++= Seq(
        compilerPlugin(("com.github.ghik" % "silencer-plugin" % silencerVersion).cross(CrossVersion.full)),
        ("com.github.ghik" % "silencer-lib" % silencerVersion % Provided).cross(CrossVersion.full)),
    crossScalaVersions := Seq(scala212, scala213),
    scalafmtOnCompile := true)
}
