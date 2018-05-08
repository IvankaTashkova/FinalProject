<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/MyProject/css/css.css">
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>DominosPizza | <c:out value="${sessionScope.pizza.name }"></c:out></title>
</head>
<body style = "background-image: linear-gradient(white 15%, #ff4040 100%);">
<div class="w3-top" style = "font-family:Arial">
  <div class="w3-bar w3-white w3-padding w3-card">
    <a href="/MyProject/home" ><img border="0"src="/MyProject/images/home-icon.png" width="40" height="40"></a>
    <!-- Right-sided navbar links. Hide them on small screens -->
    <div class="w3-right w3-hide-small" style = "font-family:Arial">
      <a href="/MyProject/home#menu" class="w3-bar-item w3-button">Menu</a>
    </div>
  </div> 
</div>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%; margin-top:150px;">
<h6 style= "color:red"><c:out value="${info}"></c:out></h6>
<h4><c:out value="${sessionScope.pizza.name}"></c:out> </h4>
<img  src="/MyProject/images/<c:out value ="${sessionScope.pizza.imgUrl}"></c:out>">
<br>
<b>Ingredients</b>
<br>
<c:forEach var= "ingredient" items="${sessionScope.ingredients}">
	<c:out value=" ${ingredient.name}"></c:out> 
</c:forEach>

<form  action="/MyProject/cart/add/${pizza.id}" method="post">
	<label for="size">Size:</label>
	<select name = "size">
		 <option value="Average">Average</option>
		 <option selected = "Selected" value="big">Big</option>
		 <option value="Jumbo">Jumbo</option>
	</select> 
<label for="dough">Dough:</label>
	<select name = "dough">
		 <option selected = "selected"value="Hand Tossed">Hand Tossed</option>
		 <option value="Italian Style">Italian Style</option>
		 <option value="Thin">Thin</option>
		 <option value="with Philadelphia">with Philadelphia</option>
	</select> 
	<input type="submit" value = "Add to cart">
	<a href="/MyProject/favorite/add/${pizza.id}" ><img border="0"src="/MyProject/images/heart.png" width="20" height="20"></a>
</form>
</div>
<br>
<br>
<br>
</body>
</html>