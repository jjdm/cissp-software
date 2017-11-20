<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Items List</title>
	</head>
	<body>
		<h1>List of Items</h1>
		<c:forEach items="${requestScope.items}" var="item">
			<p>
				<a href="./view?id=${item.id}">${item}</a>
			</p>
		</c:forEach>
	</body>
</html>