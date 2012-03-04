package com.karatebancho;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.karatebancho.servlet.SuggestServlet;

public class TomcatEmbedded {

	/**
	 * @param args
	 * @throws LifecycleException 
	 */
	public static void main(String[] args) throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(4126);
		Context ctx = tomcat.addContext("/test", "test");
		Tomcat.addServlet(ctx, "suggest", new SuggestServlet());
		ctx.addServletMapping("/suggest", "suggest");
		tomcat.start();
		tomcat.getServer().await();
	}

}
