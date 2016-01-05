package jp.modal.soul.soylatteart

import play.api.libs.json.Json

/**
 * Created by imae on 2015/12/17.
 */
case class Query(query: String, attributeNames: List[String])
object Query {
  implicit def jsonWrites = Json.writes[Query]
  implicit def jsonReads = Json.reads[Query]
}

case class QueryList(queries: List[Query]) {
  val size = queries.length
  def findTargets(directory: Directory): TargetList = {
    TargetList(queries.flatMap(directory.findTarget))
  }
}
object QueryList {
  implicit def jsonWrites = Json.writes[QueryList]
  implicit def jsonReads = Json.reads[QueryList]
}