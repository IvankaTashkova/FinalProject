<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>DominosPizza | <c:out value="${sessionScope.pizza.name }"></c:out></title>
</head>
<body>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%">
<h4><c:out value="${sessionScope.pizza.name}"></c:out> </h4>
<img  src="/MyProject/images/<c:out value ="${sessionScope.pizza.imgUrl}"></c:out>">
<br>
<b>Ingredients</b>
<br>
<c:forEach var= "ingredient" items="${sessionScope.ingredients}">
	<c:out value=" ${ingredient.name}"></c:out> 
</c:forEach>

<form  action="cart/add/{pizza.id}" method="get">
	<label for="size">Size:</label>
	<select name = "size">
		 <option value="average">Average</option>
		 <option selected = "selected" value="big">Big</option>
		 <option value="jumbo">Jumbo</option>
	</select> 
<label for="dough">Dough:</label>
	<select name = "dough">
		 <option selected = "selected"value="handtossed">Hand Tossed</option>
		 <option value="italianstyle">Italian Style</option>
		 <option value="thin">Thin</option>
		 <option value="philadelphia">with Philadelphia</option>
	</select> 
	<a href="/MyProject/cart/add/${pizza.id}" ><img border="0"src="/MyProject/images/eCommerce-ICON-small.png" width="20" height="20"></a>
	<a href="/MyProject/addFav/add/${pizza.id}" ><img border="0"src="/MyProject/images/heart.png" width="20" height="20"></a>
</form>
</div>
</body>
</html>