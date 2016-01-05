package jp.modal.soul.soylatteart

import sbt.Keys._
import sbt._

/**
 * Created by imaemasatoshi on 2015/12/17.
 */
object SoyLatteArtPlugin extends Plugin {
  override lazy val settings = Seq(
    commands ++= Seq(latteArt)
  )
  def latteArt = Command.args("latteart", "<args>") { (state, args) =>
    SoyLatteArt.execute(args)
    state
  }
}
