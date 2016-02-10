package jp.modal.soul.soylatteart.spi.jmx_server_builder

import java.io.File
import javax.management.remote.{ JMXConnectorFactory, JMXServiceURL }

import com.sun.tools.attach.VirtualMachine
import jp.modal.soul.soylatteart.{ JMXServer, JMXServerBuilder }

import scala.util.Try

/**
 * Created by imae on 2015/12/17.
 */
class LocalJMXServerBuilder(val pid: String) extends JMXServerBuilder {

  override def createJMXServer: Option[JMXServer] = {
    Try {
      val virtualMachine = VirtualMachine.attach(pid)
      val connectorAddress = getConnectorAddress(virtualMachine)
      val jmxServiceURL = new JMXServiceURL(connectorAddress)
      new JMXServer(JMXConnectorFactory.connect(jmxServiceURL).getMBeanServerConnection)
    }.toOption
  }

  final val LOCAL_CONNECTOR_ADDR = "com.sun.management.jmxremote.localConnectorAddress"

  private def getConnectorAddress(virtualMachine: VirtualMachine): String = {
    Try(virtualMachine.getAgentProperties.getProperty(LOCAL_CONNECTOR_ADDR)).toOption.getOrElse(loadAgent(virtualMachine))
  }

  private def loadAgent(virtualMachine: VirtualMachine): String = {
    try {
      val systemProperties = virtualMachine.getSystemProperties
      val javaHome = systemProperties.getProperty("java.home")
      val agent = String.join(File.separator, javaHome, "jre", "lib", "management-agent.jar")
      virtualMachine.loadAgent(agent)
      virtualMachine.getAgentProperties.getProperty(LOCAL_CONNECTOR_ADDR)
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    }
  }

}
