<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/resources/main.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>

	<div align="center">
		<h1>${name}</h1>
		<h1>${owner}</h1>

		<form method="get" action="/ProjectRestaurant/createProduct">

			<table>
				<tr>
					<td>Name:</td>
					<td><input name="name" type="text" /></td>
				</tr>
				<tr>
					<td>Price:</td>
					<td><input name="price" type="text" /></td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><input name="discription" type="text" /></td>
				</tr>
				<tr>
					<td>Minutes needed:</td>
					<td><input name="minsNeeded" type="text" /></td>
				</tr>
				<tr>
					<td>image url:</td>
					<td><textarea rows="imgUrl" type="text"></textarea></td>
				</tr>
				<tr>
					<td>Name:</td>
					<td><input name="name" type="text" /></td>
				</tr>
				<tr>
					<td>Category:</td>
					<td><select>
							<c:forEach var="category" items="${categories}">
								<option value="${category.categoryId}">"${category.name}"</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td></td>
					<td><input value="Create product" type="submit" /></td>
				</tr>
			</table>


		</form>
	</div>
</body>
</html>