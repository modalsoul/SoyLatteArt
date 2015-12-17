package jp.modal.soul.soylatteart.spi.outbound

import jp.modal.soul.soylatteart.{OutputData, Output}
import org.slf4j.LoggerFactory

/**
 * Created by imae on 2015/12/17.
 */
class LoggingOutput extends Output {
  val logger = LoggerFactory.getLogger(getClass)
  override def write(outputData: OutputData): Unit = {
    logger.info(outputData.LTSV)
  }
}
