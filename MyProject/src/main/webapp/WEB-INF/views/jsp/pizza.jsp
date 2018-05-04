<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>DominosPizza | Product</title>
</head>
<body>

<h4><c:out value="${sessionScope.pizza.name}"></c:out> </h4>

<img src="${pizza.imgUrl}">

<p>
	<c:forEach var= "ingredient" items="${sessionScope.pizza.ingredients}">
		<c:out value=" ${ingredient.name}"></c:out>,
	</c:forEach>
</p>

<form>
	<c:forEach var="extra" items="${sessionScope.pizza.extraIngredients}">
		<input type="checkbox" value="${extra.name}">
	</c:forEach>
	<select>
		 <option value="average">Average</option>
		 <option selected = "selected" value="big">Big</option>
		 <option value="jumbo">Jumbo</option>
	</select> 
	<a href="cart?id=${product.id}&action=addToCart" ><img border="0"src="eCommerce-ICON-small.png" width="20" height="20"></a>	
</form>
</body>
</html>