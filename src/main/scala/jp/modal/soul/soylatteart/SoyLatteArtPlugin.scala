package jp.modal.soul.soylatteart

import org.apache.commons.cli.{DefaultParser, HelpFormatter, Options}
import sbt._


/**
 * Created by imaemasatoshi on 2015/12/17.
 */
object SoyLatteArtPlugin extends Plugin {
  val options = new Options
  options.addOption("h", "help", false, "print this message.")
  options.addOption("l", "list", false, "print all MBean name list.")
  options.addOption("a", "attr", true, "print all readable attribute name list.")
  options.addOption("c", "config", true, "config json file url.")
  options.addOption("i", "interval", true, "interval second")

  lazy val latteArt = Command.args("latteart", "<args>") { (state, args) =>
    val cl = new DefaultParser().parse(options, args.toArray, true)
    if (cl.hasOption('h') || cl.getArgs.length <= 0) new HelpFormatter().printHelp("latteart [Options ...] pid", options)
    SoyLatteArt.execute(cl)
    state
  }
}
