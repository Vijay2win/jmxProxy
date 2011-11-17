package com.jmxproxy;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class JMXProxy
{
    public static final String DEFAULT_PORT = "7501";
    public static final String PREFIX = "JMX WorkAround: ";
    public static final String PORT_SYS_PROPERTY = "com.jmxproxy.rmi.port";
    public static final String HOST_SYS_PROPERTY = "com.jmxproxy.rmi.host";

    private JMXProxy()
    {
    }

    public static void premain(String agentArgs) throws IOException
    {

        String hostname = System.getProperty(HOST_SYS_PROPERTY, InetAddress.getLocalHost().getHostAddress());
        startJMXService(hostname);
    }

    public static void startJMXService(String hostname) throws IOException
    {
        System.setProperty("java.rmi.server.randomIDs", "true");
        int port = Integer.parseInt(System.getProperty(PORT_SYS_PROPERTY, DEFAULT_PORT));
        System.setProperty("java.rmi.server.hostname", hostname);

        System.out.println(PREFIX + "Registring RMI registry on port: " + port);
        LocateRegistry.createRegistry(port);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        System.out.println(PREFIX + "Creating Environment map");
        Map<String, Object> env = new HashMap<String, Object>();

        System.out.println(PREFIX + "Creating RMI Connector server on " + hostname);
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://" + hostname + ":" + port + "/jndi/rmi://" + hostname + ":" + port + "/jmxrmi");

        System.out.println(PREFIX + "Starting JMX Connector Server on: " + url.getURLPath());
        JMXConnectorServer jmxConn = JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);
        jmxConn.start();
    }
}
