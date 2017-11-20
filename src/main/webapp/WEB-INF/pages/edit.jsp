<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Edit Item</title>
	</head>
	<body>
		<h1>Edit Item</h1>
		<form method="GET" action="./update">
			<input type="hidden" name="id" value="${requestScope.item.id}"/>
			<input type="text" name="name" value="${requestScope.item.name}"/>
			<input type="text" name="description" value="${requestScope.item.description}"/>
			<input type="submit" value="Submit"/>
			<a href="./view?id=${requestScope.item.id}">Cancel</a>
		</form>
	</body>
</html>