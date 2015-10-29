package com.nova.study.clazz;

import java.lang.reflect.Constructor;

class Book {
	public Book() {
		System.out.println("---------default------------");
	}

	@SuppressWarnings("unused")
	private Book(String name, double price) {
		System.out
				.println("---------double params------" + name + ", " + price);
	}

	public Book(String name) {
		System.out.println("---------one param---------" + name);
	}

}

public class ConstructorClazzDemo {

	public static void main(String[] args) {
		try {
			getInvoke();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getInvoke() throws Exception {
		Class<Book> clz = Book.class;
		System.out.println("===========构造器调用1===============");
		// 这种方式只能调用默认构造方法即无参数，public类型。
		Book book = clz.newInstance();
		System.out.println(book);

		System.out.println("===========构造器调用2===============");
		Constructor<Book> c = clz.getConstructor();
		book = c.newInstance();
		System.out.println(book);

		c = clz.getConstructor(String.class);
		book = c.newInstance("启示录");
		System.out.println(book);

		System.out.println("===========构造器调用3===============");
		// 调用使用构造函数时，需使用declared
		c = clz.getDeclaredConstructor(String.class, double.class);
		// 调用类的私有构造方式时，使用反射必须设置成可访问的形式
		c.setAccessible(true);
		book = c.newInstance("启示录", 30.0);
		System.out.println(book);

	}

}
