<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Login Page</title>



</head>
<body>
	<div align="center">
		<h1>${name}</h1>
		<h1>${owner}</h1>

		<h3>Logged in as administrator with username and Password</h3>

		<form name='f' action='/ProjectRestaurant/checkAdmin' method='POST'>
			<table class="formtable">
				<tr>
					<td>User:</td>
					<td><input type='text' name='username' value=''></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input name="submit" type="submit"
						value="Login" /></td>
				</tr>
			</table>
		</form>

			<p><a href="<c:url value="/forgottenPassword"/>">Forgotten Password?</a></p>
	</div>
</body>
</html>