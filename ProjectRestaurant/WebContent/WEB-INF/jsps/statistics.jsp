<%@ page language="java" contentType="text/html;  charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/resources/main.css" rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
	<%
		if (session.getAttribute("isLogged") == null
				|| !((String) session.getAttribute("isLogged"))
						.equals("true")) {
			response.sendRedirect("/ProjectRestaurant/");
		}
	%>
	<div align="center">
		<form name='f' action='/ProjectRestaurant/home' method='GET'>
			<table class="home">
				<tr>
					<td colspan='2'><input name="home" type="submit" value="Home" /></td>
				</tr>
			</table>
		</form>
		<h1>${name}</h1>
		<h1>${owner}</h1>

		<h2>Statistics of sold products</h2>
		<table class="products">

			<tr>
				<td>Name</td>
				<td>Price</td>
				<td>QuantitySold</td>
			</tr>

			<c:forEach var="entry" items="${productsByCount}">
				<tr>

					<td align="center"><c:out value="${entry.key.name}"></c:out></td>

					<td align="center"><c:out value="${entry.key.price}"></c:out></td>

					<td align="center"><c:out value="${entry.value}"></c:out></td>

				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>