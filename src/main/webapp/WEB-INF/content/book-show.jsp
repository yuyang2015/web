<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>book-show</title>
	</head>
	
	<body>
		<p>书籍信息编辑</p>
		<form action="${book.id }" method="post">
			<input name="_method" type="hidden" value="put" />
			<input name="book.id" type="hidden" value="${book.id }" />
			<table border="1" >
				<tr>
					<td>编号</td>
					<td>${book.id}</td>
				</tr>
				<tr>
					<td>书名</td>
					<td><input type="text" value="${book.name}" name="book.name" /></td>
				</tr>
				<tr>
					<td>作者</td>
					<td><input type="text" value="${book.author}" name="book.author" /></td>
				</tr>
				<tr>
					<td>单价</td>
					<td><input type="text" value="${book.price}" name="book.price" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="submit" /></td>
					<td><input type="reset" value="reset" /></td>
				</tr>
			</table>
		</form>
	</body>
</html>

