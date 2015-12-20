sbtPlugin := true

name := "SoyLatteArt"

organization := "jp.modal.soul"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play-json_2.11" % "2.4.6",
  "org.slf4j" %"slf4j-api" % "1.7.13",
  "commons-cli" % "commons-cli" % "1.3.1",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test" withSources() withJavadoc()
)

initialCommands := "import jp.modal.soul.soylatteart._"

unmanagedJars in Compile ~= {uj =>
  Seq(Attributed.blank(file(System.getProperty("java.home").dropRight(3)+"lib/tools.jar"))) ++ uj
}