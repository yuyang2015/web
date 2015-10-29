<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>book-create</title>
	</head>
	
	<body>
		<p>添加新书籍信息</p>
		<form action="${pageContext.request.contextPath}/book" method="post">
			<table border="1" >
				<tr>
					<td>书名</td>
					<td><input type="text" name="book.name" /></td>
				</tr>
				<tr>
					<td>作者</td>
					<td><input type="text" name="book.author" /></td>
				</tr>
				<tr>
					<td>单价</td>
					<td><input type="text" name="book.price" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="submit" /></td>
					<td><input type="reset" value="reset" /></td>
				</tr>
			</table>
		</form>
	</body>
</html>

