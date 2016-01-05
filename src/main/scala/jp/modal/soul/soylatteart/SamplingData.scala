package jp.modal.soul.soylatteart

import java.net.InetAddress
import java.time.ZonedDateTime
import javax.management.{ Attribute, AttributeList, ObjectName }

import scala.util.Try

/**
 * Created by imae on 2015/12/17.
 */
case class SamplingData(objectName: ObjectName, attributes: AttributeList)
object SamplingData {
  /**
   * サンプリングエラーのファクトリ
   * @param objectName
   * @param e
   * @return
   */
  def apply(objectName: ObjectName, e: Exception): SamplingData = {
    val attributes = new AttributeList()
    attributes.add(new Attribute("exception", e))
    SamplingData(objectName, attributes)
  }
}

case class SamplingDataList(samplingTime: ZonedDateTime, hostname: String, samplingDatas: Seq[SamplingData]) {
  def lookupHostname: String = {
    Try(InetAddress.getLocalHost.getHostName).getOrElse("<<unknown>>")
  }

  val size = samplingDatas.length

  def format: OutputDataList = OutputDataList(samplingDatas.map(format(_)))

  def format(samplingData: SamplingData): OutputData = OutputData(samplingTime, hostname, samplingData)
}

object SamplingDataList {
  def apply(samplingDatas: Seq[SamplingData]): SamplingDataList = {
    SamplingDataList(ZonedDateTime.now(), lookupHostname, samplingDatas)
  }

  private def lookupHostname = Try(InetAddress.getLocalHost.getHostName).getOrElse("<<unknown>>")

}
