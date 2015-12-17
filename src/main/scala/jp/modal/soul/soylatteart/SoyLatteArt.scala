package jp.modal.soul.soylatteart

import org.apache.commons.cli.{CommandLine, HelpFormatter, DefaultParser, Options}

/**
 * Created by imae on 2015/12/17.
 */
class SoyLatteArt {
  def main(args:Array[String]): Unit = {
    val cl = parseCommandLine(args)

    val jmxServerBuilder = JMXServerBuilder.getInstance(cl.getArgs)
    val jmxServer = jmxServerBuilder.createJMXServer

    if(cl.hasOption("l")) printObjectNameList(jmxServer)

//    Option(cl.getOptionValue("c")).map()


  }


  private def parseCommandLine(args:Array[String]):CommandLine = {
    val options = new Options
    options.addOption("h", "help", false, "print this message.")
    options.addOption("l", "list", false, "print all MBean name list.")
    options.addOption("a", "attr", true, "print all readable attribute name list.")
    options.addOption("c", "config", true, "config json file url.")
    options.addOption("i", "interval", true, "interval second")

    val defaultParser = new DefaultParser
    val cl = defaultParser.parse(options, args, true)

    if(cl.hasOption("h")) printHelp(options)
    if(cl.getArgs.length <= 0) printHelp(options)
    cl
  }

  private def printHelp(options:Options): Unit ={
    val helpFormatter = new HelpFormatter
    helpFormatter.printHelp("soylatteart [Options ...] pid", options)
    System.exit(0)
  }

  private def printObjectNameList(jMXServer: JMXServer): Unit ={
    (jMXServer.findAllObjectName.map{
      _.getCanonicalName
    }).toSeq.sorted.foreach(println)
    System.exit(0)
  }
}
