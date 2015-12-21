package jp.modal.soul.soylatteart.spi.jmx_server_builder

import java.io.File
import javax.management.remote.{JMXConnectorFactory, JMXServiceURL}

import com.sun.tools.attach.VirtualMachine
import jp.modal.soul.soylatteart.{JMXServer, JMXServerBuilder}

/**
 * Created by imae on 2015/12/17.
 */
class LocalJMXServerBuilder(val pid:String) extends JMXServerBuilder {

  override def createJMXServer:JMXServer = {
    var virtualMachine:VirtualMachine = null

    try {
      virtualMachine = VirtualMachine.attach(pid)

      val connectorAddress = getConnectorAddress(virtualMachine)

      val jmxServiceURL = new JMXServiceURL(connectorAddress)

      Option(JMXConnectorFactory.connect(jmxServiceURL)).map {
        jmxConnector =>
          try {
            new JMXServer(jmxConnector.getMBeanServerConnection)
          } catch {
            case e:Exception =>
              throw new RuntimeException(e)
          }
      }.get
    } catch {
      case e:Exception =>
        throw new RuntimeException(e)
    }
  }

  final val LOCAL_CONNECTOR_ADDR = "com.sun.management.jmxremote.localConnectorAddress"

  private def getConnectorAddress(virtualMachine:VirtualMachine):String = {
    try {
      Option(virtualMachine.getAgentProperties.getProperty(LOCAL_CONNECTOR_ADDR)).getOrElse(loadAgent(virtualMachine))
    } catch {
      case e:Exception =>
        throw new RuntimeException(e)
    }
  }

  private def loadAgent(virtualMachine: VirtualMachine):String = {
    try {
      val systemProperties = virtualMachine.getSystemProperties

      val javaHome = systemProperties.getProperty("java.home")
      val agent = String.join(File.separator, javaHome, "jre/lib", "management-agent.jar")
      virtualMachine.loadAgent(agent)
      virtualMachine.getAgentProperties.getProperty(LOCAL_CONNECTOR_ADDR)
    } catch {
      case e:Exception =>
        throw new RuntimeException(e)
    }
  }

}
