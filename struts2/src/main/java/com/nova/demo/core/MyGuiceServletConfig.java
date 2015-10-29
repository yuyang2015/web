package com.nova.demo.core;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.struts2.Struts2GuicePluginModule;
import com.nova.demo.servlet.UserServlet;

public class MyGuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new Struts2GuicePluginModule(),
				new ServletModule() {

					@Override
					protected void configureServlets() {
						super.configureServlets();
						// Struts 2 setup
						bind(StrutsPrepareAndExecuteFilter.class).in(
								Singleton.class);
						filter("/*").through(
								StrutsPrepareAndExecuteFilter.class);
						// 绑定servlet,注意需要在stuts.xml配置struts自身的拦截的请求为action,排除拦截servlet
						this.serve("/userServlet").with(UserServlet.class);
					}

				});
	}

}
