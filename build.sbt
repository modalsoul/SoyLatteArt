name := "SoyLatteArt"

sbtPlugin := true

organization := "jp.modal.soul"

version := "0.2"

scalaVersion := "2.12.4"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play-json_2.12" % "2.6.7",
  "org.slf4j" %"slf4j-api" % "1.7.25",
  "commons-cli" % "commons-cli" % "1.4",
  "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test" withSources() withJavadoc()
)

initialCommands := "import jp.modal.soul.soylatteart.SoyLatteArtPlugin._"
