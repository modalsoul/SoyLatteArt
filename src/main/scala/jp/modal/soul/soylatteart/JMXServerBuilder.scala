package jp.modal.soul.soylatteart

import jp.modal.soul.soylatteart.spi.jmx_server_builder.LocalJMXServerBuilder

/**
 * Created by imae on 2015/12/17.
 */
trait JMXServerBuilder {
  def createJMXServer:JMXServer
}

object JMXServerBuilder {
  def getInstance(args:String*):JMXServerBuilder = {
    new LocalJMXServerBuilder(args(1))
  }
}