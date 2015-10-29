package com.nova.demo.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("struts-default")
@Action("user")
@Results({ @Result(name = "success", location = "/login.jsp") })
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	public String loginInput() {
		System.out.println("abc");
		return SUCCESS;
	}

	public String login() {
		System.out.println(username + "--" + password);
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
