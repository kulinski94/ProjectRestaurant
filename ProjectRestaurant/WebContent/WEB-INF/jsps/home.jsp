<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

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
		<h1>${name}</h1>
		<h1>${owner}</h1>
		<ol>
			<li><a href="<c:url  value="/addProduct"/>">Add Product</a></li>
			<li><a href="<c:url value="/statistics"/>">Statistics</a></li>
			<li><a href="<c:url value="/dailyReport"/>">Daily Report</a></li>
			<li><a href="<c:url value="/registerWaiter"/>">Register new	waiter</a></li>
			<li><a href="<c:url value="/logoutAdmin"/>">Logout</a></li>
		</ol>
	</div>
</body>
</html>