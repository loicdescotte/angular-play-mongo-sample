import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "angular-app"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
  )

}