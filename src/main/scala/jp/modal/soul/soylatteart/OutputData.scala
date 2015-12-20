package jp.modal.soul.soylatteart

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.management.openmbean.CompositeDataSupport
import javax.management.{AttributeList, ObjectName}

import collection._
import collection.JavaConversions._

/**
 * Created by imae on 2015/12/17.
 */
class OutputData(val outputData:Map[String, Object]) {
  def LTSV:String = {
    outputData.entrySet().map {
      entry =>
        s"${entry.getKey}:${entry.getValue}"
    }.mkString("\t")
  }
}

object OutputData {
  final val SAMPLING_TIME_KEY = "time"
  final val HOSTNAME_KEY = "hostname"
  final val DOMAIN_KEY = "domain"
  final val OBJECT_NAME_KEY = "objectName"
  final val dateTimeformatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

  def apply(samplingTime:ZonedDateTime, hostname:String, samplingDate:SamplingData):OutputData = {
    val map = Map[String, Object](
      SAMPLING_TIME_KEY -> samplingTime.format(dateTimeformatter),
      HOSTNAME_KEY -> hostname
    )
    new OutputData(map ++: parseSamplingData(samplingDate))
  }

  private def parseSamplingData(samplingData:SamplingData):mutable.Map[String, Object] = {
    val hashMap = new java.util.LinkedHashMap[String, Object]()
    hashMap.putAll(parseAttributes(samplingData.attributes))
    hashMap
  }

  private def parseObjectName(objectName:ObjectName):mutable.Map[String, Object] = {
    val map = new java.util.LinkedHashMap[String, Object]()
    map.put(OBJECT_NAME_KEY, objectName.getCanonicalName)
    map.put(DOMAIN_KEY, objectName.getDomain)
    map.putAll(objectName.getKeyPropertyList)
    map
  }

  private def parseAttributes(attributes:AttributeList):mutable.Map[String, Object] = {
    val map = new java.util.LinkedHashMap[String, Object]()
    attributes.asList().foreach{
      attribute =>
        attribute.getValue match {
          case value:CompositeDataSupport =>
            map.putAll(parseCompositeDataSupport(value))
          case value:Any =>
            map.put(attribute.getName, value)
        }
    }
    map
  }

  private def parseCompositeDataSupport(compositeDataSupport:CompositeDataSupport):Map[String, Object] = {
    val compositeType = compositeDataSupport.getCompositeType
    val map = new java.util.LinkedHashMap[String, Object]()
    compositeType.keySet().foreach {
      key =>
        compositeDataSupport.get(key) match {
          case value:CompositeDataSupport =>
            map.put(key, value)
          case value:Any =>
            map.putAll(parseCompositeDataSupport(value.asInstanceOf[CompositeDataSupport]))
        }
    }
    map
  }
}

trait Output {
  def write(outputData:OutputData)
}

case class OutputDataList(outputDatas:Seq[OutputData]) {
  val size = outputDatas.length
  def send(output:Output): Unit = outputDatas.foreach(output.write)
}