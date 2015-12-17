package jp.modal.soul.soylatteart

/**
 * Created by imae on 2015/12/17.
 */
trait Directory {
  def findTarget(query:Query):Seq[Target]
}
