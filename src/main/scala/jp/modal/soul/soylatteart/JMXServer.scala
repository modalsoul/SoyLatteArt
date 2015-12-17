package jp.modal.soul.soylatteart

import java.io.IOException
import javax.management._

import org.slf4j.LoggerFactory

import collection._
import collection.JavaConversions._

/**
 * Created by imae on 2015/12/17.
 */
case class JMXServer(mBeanServerConnection:MBeanServerConnection) extends Directory with Monitor{
  def findAllObjectName:Set[ObjectName] = {
    try {
      mBeanServerConnection.queryNames(new ObjectName("*:"), null)
    } catch {
      case e:_ =>
        throw new RuntimeException(e)
    }
  }

  def findReadableAttributeInfoByObjectName(objectName:String):Set[MBeanAttributeInfo] = {
    try {
      val mBeanInfo = mBeanServerConnection.getMBeanInfo(new ObjectName(objectName))
      mBeanInfo.getAttributes.filter(_.isReadable).toSet
    } catch {
      case e:_ =>
        throw new RuntimeException(e)
    }
  }

  override def findTarget(query: Query): Seq[Target] = {
    try {
      val logger = LoggerFactory.getLogger(getClass)
      logger.info(s"query:${query.query}")
      val on = mBeanServerConnection.queryNames(new ObjectName(query.query), null)
      logger.info(s"size:${on.size}")

      mBeanServerConnection.queryNames(new ObjectName(query.query), null).map {
        objectName =>Target(objectName, query.attributeNames)
      }.toSeq
    } catch {
      case e:_ =>
        throw new RuntimeException(e)
    }
  }

  override def sampling(target: Target): SamplingData = {
    try {
      SamplingData(target.objectName, mBeanServerConnection.getAttributes(target.objectName, target.attributeNames.toArray))
    } catch {
      case e:InstanceNotFoundException =>
        SamplingData(target.objectName, e)
      case e:ScalaReflectionException =>
        SamplingData(target.objectName, e)
      case e:_ =>
        throw new RuntimeException(e)
    }
  }
}
