<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>DominosPizza|Shopping Cart</title>
</head>
<body>
<table border="0">
		<tr>
			<th>Name</th>
			<th>Ingredients</th>
			<th>Size</th>
			<th>Price</th>
			<th>Quantity</th>
			<th>Total</th>
			<th></th>
		</tr>
		<c:set var="sum" value = "0"></c:set>
		<c:forEach var="item" items="${sessionScope.cart}">
		<c:set var = "sum" value="${sum + item.key.price*item.value}"></c:set>
			<tr>
				<td><c:out value="${item.key.name}"></c:out></td>
				<td>
				ingredients
				</td>
				<td><c:out value="${item.key.size}"></c:out> </td>
				<td><c:out value="${ item.key.price }"></c:out></td>
				<td align="center"><c:out value="${ item.value }"></c:out></td>
				<td align="center"><c:out value="${ item.key.price*item.value }"></c:out></td>
				<td>
					<a href = "cart?id=${item.key.id}&action=delete">Delete</a>						
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan = "6" align = "right">Sum:<c:out value = "${sum }"></c:out></td>
			</tr>
			
</table>
<a href = "menu.jsp">Continue shopping</a> 
<p style = "padding-left:350px;"><button href="makeorder" align = "right">Order now</button></p>
</body>
</html>