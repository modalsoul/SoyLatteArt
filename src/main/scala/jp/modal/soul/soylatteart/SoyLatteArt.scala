package jp.modal.soul.soylatteart

import java.util.concurrent.{ Executors, TimeUnit }

import org.apache.commons.cli.{ DefaultParser, HelpFormatter, Options }

/**
 * Created by imae on 2015/12/17.
 */
object SoyLatteArt {
  val options = new Options
  options.addOption("h", "help", false, "print this message.")
  options.addOption("l", "list", false, "print all MBean name list.")
  options.addOption("a", "attr", true, "print all readable attribute name list.")
  options.addOption("c", "config", true, "config json file url.")
  options.addOption("i", "interval", true, "interval second")

  val scheduler = Executors.newScheduledThreadPool(1)

  def execute(args: Seq[String]): Unit = {
    val commandLine = new DefaultParser().parse(options, args.toArray, true)
    if (commandLine.hasOption('h') || commandLine.getArgs.length <= 0) new HelpFormatter().printHelp("latteart [Options ...] pid", options)

    val jmxServerBuilder = JMXServerBuilder.getInstance(commandLine.getArgs(): _*)

    jmxServerBuilder.createJMXServer.foreach { jmxServer =>
      if (commandLine.hasOption('l')) printObjectNameList(jmxServer)
      Option(commandLine.getOptionValue('a')).foreach(printReadableAttributeList(jmxServer))
      val confOpt = Option(commandLine.getOptionValue('c')).map(LatteConf.load).getOrElse(LatteConf.load())

      confOpt.foreach { conf =>
        val monitoringService = MonitoringService(jmxServer, conf.queries, conf.outputs)
        val interval = commandLine.getOptionValue('i').map(_.toLong)
        if (interval.isEmpty) monitoringService.run
        else {
          scheduler.scheduleAtFixedRate(
            new Runnable { override def run(): Unit = monitoringService.run },
            0L,
            interval.head,
            TimeUnit.SECONDS)
        }
      }
    }
  }

  private def printObjectNameList(jMXServer: JMXServer): Unit = {
    jMXServer.findAllObjectName.map(_.getCanonicalName).toSeq.sorted.foreach(println)
  }

  private def printReadableAttributeList(jMXServer: JMXServer)(objectName: String): Unit = {
    jMXServer.findReadableAttributeInfoByObjectName(objectName).map(
      attributeInfo =>
        s"${attributeInfo.getName} <type:${attributeInfo.getType}>").seq.foreach(println)
  }
}
