import sbt._

object IntegrationTesting extends Build {

  lazy val root =
    Project("root", file("."))
      .configs( IntegrationTest )
      .settings( Defaults.itSettings : _* )

}