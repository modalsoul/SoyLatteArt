package jp.modal.soul.soylatteart

/**
 * Created by imae on 2015/12/17.
 */
case class Query(query:String, attributeNames:Seq[String])

case class QueryList(queries:Seq[Query]) {
  val size = queries.length
  def findTargets(directory:Directory):TargetList = {
    TargetList(queries.flatMap(directory.findTarget))
  }
}