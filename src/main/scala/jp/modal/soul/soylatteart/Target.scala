package jp.modal.soul.soylatteart

import javax.management.ObjectName

/**
 * Created by imae on 2015/12/17.
 */
case class Target(objectName: ObjectName, attributeNames: Seq[String])

case class TargetList(targets: Seq[Target]) {
  val size = targets.length
  def sampling(monitor: Monitor): SamplingDataList = {
    SamplingDataList(targets.map(monitor.sampling(_)))
  }
}