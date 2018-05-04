<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>DominosPizza | Order</title>
</head>
<body>
<table>
	<tr>
		<th>Product</th>
		<th>Quantity</th>
		<th>Price</th>
	</tr>
	<c:forEach var = "product" items = "${sessionScope.productsInOrder}">
		<tr>
			<td><c:out value="${product.key.name}"></c:out> </td>
			<td><c:out value="${product.value}"></c:out></td>
			<td><c:out value="${product.key.price*product.value}"></c:out></td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="3"><c:out value="${sessionScope.totalPrice }"></c:out> </td>
	</tr>
</table>
<form>
<p>Choose address:</p>
<br>
	<p>Select from you addresses:</p>
	<select>
		<c:forEach var="address" items="sessionScope.adresses">
		<option><c:out value="${address.location}"></c:out></option>
		</c:forEach>
	</select> 
	<p>or</p>
	<p>Add new address:</p>
	<input type="text" name="address" placeholder="address">
	<p>or</p>
	<p>You can take you order from one of our restaurants! Choose one:</p>
 	<input type="checkbox" name="fromrestaurant" value="Take your order from restaurant">
 	<div id="map" style="display:none;">
   		<select>
			<c:forEach var="restaurant" items="${sessionScope.restaurants}">
				<option><c:out value="${restaurant.address}"></c:out></option>
			</c:forEach>
		</select> 
		<jsp:include page="map.jsp">
		</jsp:include>
	</div>
	<button href="">Make Order</button>
</form>

<script type="text/javascript">
$('#checkbox').change(function() {
    $('#map').toggle();
});
</script>
</body>
</html>