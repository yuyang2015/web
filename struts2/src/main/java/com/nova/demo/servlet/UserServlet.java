package com.nova.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

@Singleton
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		String type = request.getParameter("type");
		if ("login_input".equals(type)) {
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		} else if ("login".equals(type)) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			System.out.println("name->" + name + ",password->" + password);
		}
	}

}
