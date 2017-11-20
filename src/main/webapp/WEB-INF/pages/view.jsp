<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>View Item</title>
	</head>
	<body>
		<h1>View Item</h1>
		<p>${requestScope.item}</p>
		<a href="./list">List</a>
		<a href="./edit?id=${requestScope.item.id}">Edit</a>
		<a href="./delete?id=${requestScope.item.id}">Delete</a>
	</body>
</html>