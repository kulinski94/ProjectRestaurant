<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New waiter</title>
</head>
<body>

	<div align="center">
		<h1>${name}</h1>
		<h1>${owner}</h1>

<form method="get" action="/ProjectRestaurant/createProduct">

			<table>
				<tr>
					<td>username:</td>
					<td><input name="username" type="text" /></td>
				</tr>
				<tr>
					<td>password:</td>
					<td><input name="password" type="password" /></td>
				</tr>
				<tr>
					<td>password:</td>
					<td><input name="password" type="password" /></td>
				</tr>
				<tr>
					<td>first name:</td>
					<td><input name="minsNeeded" type="text" /></td>
				</tr>
				<tr>
					<td>last name:</td>
					<td><input name="minsNeeded" type="text" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input value="Create waiter account" type="submit" /></td>
				</tr>
			</table>


		</form>






	</div>
</body>
</html>