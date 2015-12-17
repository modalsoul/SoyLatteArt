package jp.modal.soul.soylatteart

import sbt._
import sbt.Keys._
import com.typesafe.config._


/**
 * Created by imaemasatoshi on 2015/12/17.
 */
object SoyLatteArtPlugin extends Plugin {

  lazy val latteArt = Command.command("latteart") { state =>

    state
  }

}
