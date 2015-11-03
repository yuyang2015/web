<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<body>
	<h6>1、重定向页面</h6>
	<form:form method="GET" action="/springMvc/redirect">
		<table>
			<tr>
				<td><input type="submit" value="Redirect Page" /></td>
			</tr>
		</table>
	</form:form>
	<br>
	<h6>2、调用静态页面</h6>
	<form:form method="GET" action="/springMvc/htmlPage">
		<table>
			<tr>
				<td><input type="submit" value="get statis Page" /></td>
			</tr>
		</table>
	</form:form>
	<br>
	<h6>3、Hello SpringMvc</h6>
	<a href="/springMvc/hello">sayHello</a>
	<h6>4、Say Hello To Tom</h6>
	<a href="/springMvc/rest/sayHello/Tom">Say Hello To Tom</a>
</body>
</html>