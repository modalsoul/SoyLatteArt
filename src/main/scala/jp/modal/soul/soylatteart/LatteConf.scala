package jp.modal.soul.soylatteart

import java.net.URL

import jp.modal.soul.soylatteart.spi.outbound.LoggingOutput
import jp.modal.soul.soylatteart.util.Loan
import play.api.libs.json.Json


/**
 * Created by imae on 2015/12/20.
 */
class LatteConf(val queries:QueryList, val outputs:List[Output])

object LatteConf {
  def load(path:String = "./conf/monitor.json"):Option[LatteConf] = {
    try {
      var ql:QueryList = null
      for(is <- Loan(new URL(path).openStream())) {
        ql = Json.parse(is).validate[QueryList].get
      }
      Option(new LatteConf(ql, List(new LoggingOutput)))
    } catch {
      case e:Exception =>
        None
    }
  }
}
