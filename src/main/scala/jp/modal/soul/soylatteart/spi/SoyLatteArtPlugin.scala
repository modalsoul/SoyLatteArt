package jp.modal.soul.soylatteart.spi

import jp.modal.soul.soylatteart.SoyLatteArt
import sbt._
import Keys._

/**
 * Created by imaemasatoshi on 2015/12/17.
 */
object SoyLatteArtPlugin extends AutoPlugin {
  override lazy val projectSettings = Seq(
    commands += latteArt
  )
  def latteArt = Command.args("latteart", "<args>") { (state, args) =>
    SoyLatteArt.execute(args)
    state
  }
}
