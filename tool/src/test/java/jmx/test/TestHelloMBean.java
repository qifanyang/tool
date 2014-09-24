package jmx.test;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * 
 * jmx jar 下载Java Management Extension (JMX) 1.2.1 包含了HtmlAdaptorServer
 * http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-java-plat-419418.html#7657-jmx-1.2.1-oth-JPR
 *@author TOBE
 *
 */

public class TestHelloMBean {

	/*
	 * For simplicity, we declare "throws Exception". Real programs will usually
	 * want finer-grained exception handling.
	 */
	public static void main(String[] args) throws Exception {
		// Get the Platform MBean Server
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// Construct the ObjectName for the MBean we will register
		ObjectName name = new ObjectName("com.example.mbeans:type=Fuck");

		// Create the Hello World MBean
		Hello mbean = new Hello();

		// Register the Hello World MBean
		mbs.registerMBean(mbean, name);

		// Wait forever
		// System.out.println("Waiting forever...");
		// Thread.sleep(Long.MAX_VALUE);
		
		  ObjectName adapterName = new ObjectName("HelloAgent:name=htmladapter,port=8082");
	        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
	        mbs.registerMBean(adapter, adapterName);

	        adapter.start();
	        System.out.println("start.....");
	}
}
