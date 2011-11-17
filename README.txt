If you care about JConsole for troubleshooting read on. 
I was able to make JConsole work for jvm in aws env, the change is motivation @ http://blogs.oracle.com/jmxetc/entry/connecting_through_firewall_using_jmx with some modifications.

To use it, You need set the following :
-Dcom.jmxproxy.rmi.host=`public-hostname`
-Dcom.jmxproxy.rmi.port=$JMX_PORT
-javaagent:$CLASS_PATH/lib/jmxproxy-<VERSION>.jar"

RMI server runs System.gc every 1 hour and we dont want to enable DisableExplicitGC option by default. And right now this is useful only for debugging and hence you can bring down a machine and append to it.

What if you don't want to bring the machine down?
there is cmdline-jmxclient-0.10.3.jar in  /apps/nfcassandra_server/bin/ (available since 0.8.6) to run commands. + Opscenter and Epic to get some details.