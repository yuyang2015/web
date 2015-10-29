<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>book-index</title>
	</head>
	<body>
		<p>图书列表</p>
		<a href="${pageContext.request.contextPath}/book/new">新增</a>
		<table border="1">
			<thead>
				<tr>
					<td>编号</td>
					<td>书名</td>
					<td>作者</td>
					<td>定价</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="m" items="${map }">
					<tr>
						<td>${m.value.id }</td>
						<td>${m.value.name }</td>
						<td>${m.value.author }</td>
						<td>${m.value.price }</td>
						<td><a href="${pageContext.request.contextPath}/book/${ m.value.id }">编辑</a>|<a href="${pageContext.request.contextPath}/book/${ m.value.id }?_method=delete">删除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>

