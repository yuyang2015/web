<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<body>
	<h3>重定向页面</h3>
	<form:form method="GET" action="/springMvc/redirect">
		<table>
			<tr>
				<td><input type="submit" value="Redirect Page" /></td>
			</tr>
		</table>
	</form:form>
	<br>
	<h3>调用静态页面</h3>
	<form:form method="GET" action="/springMvc/htmlPage">
		<table>
			<tr>
				<td><input type="submit" value="get statis Page" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>