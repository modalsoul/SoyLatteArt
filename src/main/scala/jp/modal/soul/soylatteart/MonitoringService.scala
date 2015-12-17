package jp.modal.soul.soylatteart

import org.slf4j.LoggerFactory

/**
 * Created by imae on 2015/12/17.
 */
class MonitoringService(val jmxServer:JMXServer, targetList:TargetList, outputs:Seq[Output]) {
  private val logger = LoggerFactory.getLogger(getClass)

  logger.trace(s"targets size:${targetList.size}")

  def run: Unit ={
    logger.debug("start")
    val samplingDataList = targetList.sampling(jmxServer)
    val outputDataList = samplingDataList.format
    outputs.foreach(outputDataList.send)
    logger.debug("end")
  }

}


