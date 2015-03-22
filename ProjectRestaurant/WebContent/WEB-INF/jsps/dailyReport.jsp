<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body><%if (session.getAttribute("isLogged") == null || !((String)
		session.getAttribute("isLogged")) .equals("true")) {
		response.sendRedirect("/ProjectRestaurant/");} %>
<%! long sum = 0; %>
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
			<table>
				<c:forEach var="item" items="${bills}">
					<tr>

						<td align="center">BillID:<c:out value="${item.getBillId()}"></c:out></td>

						<td align="center">Total bill:<c:out
								value="${item.getTotalBill()}" ></c:out></td>
						<c:forEach var="product" items="${item.getOrders()}">
							<tr>
								<td align="center"><c:out
										value="${product.getProduct().toString()}"></c:out><br></td>

								<td align="center"><c:out value="${product.getDate()}"></c:out><br></td>
							</tr>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
			<h3>Daily turnover ${turnover} euros</h3>
	</div>
	
	
</body>
</html>