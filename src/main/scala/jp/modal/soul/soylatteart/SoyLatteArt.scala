package jp.modal.soul.soylatteart

import java.util.concurrent.{Executors, TimeUnit}

import org.apache.commons.cli.CommandLine

/**
 * Created by imae on 2015/12/17.
 */
object SoyLatteArt {
  val scheduler = Executors.newScheduledThreadPool(1)

  def execute(commandLine: CommandLine): Unit = {

    val jmxServerBuilder = JMXServerBuilder.getInstance(commandLine.getArgs():_*)
    val jmxServer = jmxServerBuilder.createJMXServer

    if(commandLine.hasOption('l')) printObjectNameList(jmxServer)

    Option(commandLine.getOptionValue('a')).foreach(printReadableAttributeList(jmxServer))

    val confOpt = Option(commandLine.getOptionValue('c')).map(LatteConf.load).getOrElse(LatteConf.load())

    confOpt.foreach { conf =>
      val monitoringService = MonitoringService(jmxServer, conf.queries, conf.outputs)
      val interval = commandLine.getOptionValue('i').map(_.toLong)

      if(interval.isEmpty) monitoringService.run
      else {
        scheduler.scheduleAtFixedRate(new Runnable {
          override def run(): Unit = monitoringService.run
        },
        0L,
        interval.head,
        TimeUnit.SECONDS
        )
      }
    }
  }

  private def printObjectNameList(jMXServer: JMXServer): Unit ={
    jMXServer.findAllObjectName.map(_.getCanonicalName).toSeq.sorted.foreach(println)
  }

  private def printReadableAttributeList(jMXServer: JMXServer)(objectName:String): Unit = {
    jMXServer.findReadableAttributeInfoByObjectName(objectName).map(
      attributeInfo =>
        s"${attributeInfo.getName} <type:${attributeInfo.getType}>"
    ).seq.foreach(println)
  }
}
