package com.nova.study.clazz;

import java.lang.reflect.Method;

class User {
	public String sayHi(String name) {
		System.out.println("hi " + name + " !");
		return "success";
	}

	public String sayHi() {
		System.out.println("hi everyone!");
		return "success";
	}

	@SuppressWarnings("unused")
	private String sayHello(String name, int age) {
		System.out.println("hi " + name + ", " + age);
		return "success";
	}
}

public class MethodClazzDemo {

	public static void main(String[] args) throws Exception {
		getMethod();
	}

	public static void getMethod() throws Exception {
		Class<User> clz = User.class;
		// 以下方法获取所有的方法，包括父类的
		Method[] m = clz.getMethods();
		for (Method method : m) {
			System.out.println(method);
		}

		m = clz.getDeclaredMethods();
		System.out.println("------------------------------");
		// 获取本类的方法
		for (Method method : m) {
			System.out.println(method);
		}

		System.out.println("------------------------------");
		// sayHi方法的调用
		Method method = clz.getDeclaredMethod("sayHi", String.class);
		String success = method.invoke(clz.newInstance(), "Tom").toString();
		System.out.println(success);

		System.out.println("-------------------------------");
		// 调用私有sayHello方法
		method = clz.getDeclaredMethod("sayHello", String.class, int.class);
		// 调用之前需要设定私有方法有权限调用
		method.setAccessible(true);
		success = method.invoke(clz.newInstance(), "Tom", 13).toString();

	}

}
