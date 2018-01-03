package jp.modal.soul.soylatteart

import sbt._
import Keys._
import complete.DefaultParsers._

/**
 * Created by imaemasatoshi on 2015/12/17.
 */
object SoyLatteArtPlugin extends AutoPlugin {

  object autoImport {
    val latteart = inputKey[Unit]("LatteArt")
  }
  import autoImport._

  override lazy val projectSettings = Seq(
    latteart := {
      val args = spaceDelimited("").parsed
      SoyLatteArt.execute(args)
    })

}
