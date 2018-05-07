
<%@page import="com.example.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%  User user  = (User) session.getAttribute("user"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/MyProject/css/css.css">
<title>DominosPizza | My profile</title>
<style>

.sidenav {
    height: 100%;
    width: 220px;
    position: fixed;
    z-index: 1;
    top: 0;
    left: 0;
    background-color: red;
    overflow-x: hidden;
    padding-top: 100px;
}

.sidenav a {
	background-color:red;
	border:0;
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 20px;
    color: white;
    display: block;
    opacity:0.75;
}

.onclick a {
	background-color:white;
	border:0;
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 20px;
    color: red;
    display: block;
    opacity:0.75;
}

.sidenav a:hover {
    background-color:white;
    color:red;
    border:0;
}


@media screen and (max-height: 450px) {
    .sidenav {padding-top: 100px;}
    .sidenav button {font-size: 18px;}
}
</style>
</head>
<body  style = "background-image: linear-gradient(white 15%, #ff4040 100%);">

<div class="sidenav">
	<img src="/MyProject/images/user-icon.png" width = "80" style = "margin-top:5px;opacity:1;possition:center;display: block;margin-top:50px;margin-left: auto; margin-right: auto;">
 	<h3 style= "display: block;opacity:0.75;border: none;padding: 6px 8px 6px 16px;color: white;font-size: 20px;text-align:center;"><%= user.getFirstName()%> <%= user.getLastName()%></h3>
   <a href = "/MyProject/profile#profile">Edit profile</a>
  <a href = "/MyProject/profile#favorite">Favorite</a>
 <a href = "/MyProject/profile#cart">Shopping Cart</a>
 <a href = "/MyProject/profile#orders">View Orders</a>
 <a href = "/MyProject/profile#addresses">My addresses</a>
</div>
<div class="w3-top" style = "font-family:Arial">
  <div class="w3-bar w3-white w3-padding w3-card">
    <a href="/MyProject/home" ><img border="0"src="/MyProject/images/home-icon.png" width="40" height="40"></a>
    <!-- Right-sided navbar links. Hide them on small screens -->
    <div class="w3-right w3-hide-small" style = "font-family:Arial">
      <a href="/MyProject/profile"><img src = "/MyProject/images/user-icon.png" width = "25" height = "25" style = "margin-top:0px; margin-bottom:0;"></a>
      <a href="/MyProject/home#menu" class="w3-bar-item w3-button">Menu</a>
      <form action = "/MyProject/logout" method = "post"><input type= "submit" class="w3-bar-item w3-button" value = "Logout"></form>
    </div>
  </div> 
</div>
<div id="profile" style = " margin: auto; text-align:center;">
<p style = "color:red;"><c:out value=" ${info}"></c:out></p>
<h3 style= "margin-top:100px;border: none;color: black;padding: 10px 150px;display: inline-block;font-size: 18px;  border-radius: 10px;">Profile</h3>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%">
	<form action = "/MyProject/profile/update/${user.id}" method = "post">	
	<table style = "margin: auto;">
	<tr>
					<td>First name:<input type="text" placeholder="<%= user.getFirstName()%>" name = "firstname"></td>
	</tr>
	<tr>
					<td>Lastname:<input type="text" placeholder="<%= user.getLastName()%>" name = "lastname"></td>
	</tr>
	<tr>
					<td>Username:<input type="text" placeholder="<%= user.getUsername()%>" name = "username"></td>
	</tr>
	<tr>
					<td>Password:<input type="password" placeholder="**********" name = "password"></td>
	</tr>
	<tr>
					<td>Confirm password:<input type="password" placeholder="**********" name = "confirmpassword"></td>
	</tr>
	<tr>
					<td>Email:<input type="email" placeholder="<%= user.getEmail()%>" name = "email"></td>
	</tr>
	<tr>
					<td>Phone:<input type="text" placeholder="<%= user.getPhoneNumber()%>" name = "phoneNumber"></td>
	</tr>		
	<tr>
					<td><input type="submit" value= "Save changes" style = "background-color:red;color:white;border:none;border-radius:5px;height:50px;opacity:0.75;"></td>
	</tr>		
	</table>
	</form>
	</div>
</div>
<br>
<br>
<div id="favorite" style = " margin: auto; text-align:center;">
<h3 style= "border: none;color: black;padding: 10px 150px;display: inline-block;font-size: 18px;  border-radius: 10px;">Favorite products</h3>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%">
	<table border="0" style = "margin: auto;">
		<tr>
			<th>Name</th>
			<th>Price</th>
			<th></th>
		</tr>
		<c:forEach var="item" items="${favorites}">
			<tr>
			<td><c:out value="${item.name}"></c:out></td>
			<td><c:out value="${item.price}"></c:out></td>
			<td><a href = "/MyProject/favorite/remove/${item.id}">Remove</a></td>
			</tr>
		</c:forEach>
	</table>
</div>
</div>
<br>
<br>
<div id="cart" style = " margin: auto; text-align:center;">
<h3 style= "border: none;color: black;padding: 10px 150px;display: inline-block;font-size: 18px;  border-radius: 10px;">Shopping cart</h3>
	<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%">
	<table border="0" style = "margin: auto;">
		<tr>
			<th>Name</th>
			<th>Size</th>
			<th>Dough</th>
			<th>Price</th>
			<th>Quantity</th>
			<th>Total</th>
			<th></th>
		</tr>
		<c:set var="sum" value = "0"></c:set>
		<c:forEach var="item" items="${cart}">
		<c:set var = "sum" value="${sum + item.key.price*item.value}"></c:set>
			<tr>
				<td><c:out value="${item.key.name}"></c:out></td>
				<td><c:out value="${item.key.size}"></c:out> </td>
				<td>
				</td>
				<td><c:out value="${ item.key.price }"></c:out></td>
				<td align="center"><c:out value="${ item.value }"></c:out></td>
				<td align="center"><c:out value="${ item.key.price*item.value }"></c:out></td>
				<td>
					<a href = "/MyProject/cart/delete/${item.key.id}">Delete</a>						
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan = "6" align = "right">Sum:<c:out value = "${sum }"></c:out></td>
			</tr>
			
</table>
<a href = "/MyProject/home">Continue shopping</a> 
<p style = "padding-left:350px;"><a href="/MyProject/order" align = "right">Order now</a></p>
</div>
</div>
<br>
<br>
<div id="orders" style = " margin: auto; text-align:center;">
<h3 style= "border: none;color: black;padding: 10px 150px;display: inline-block;font-size: 18px;  border-radius: 10px;">Orders</h3>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%">
<h4 style = "color:white;"><c:out value="${addressMessage}"></c:out></h4>
<c:if test="${not empty orders}">
<c:forEach var="order" items="${orders}">
	<table style="margin:auto">
		<tr>
			<td><c:out value="${order.date}"></c:out></td>
			<td><c:out value="${order.status}"></c:out></td>
		</tr>

	</table>
</c:forEach>
</c:if>
</div>
</div>
<br>
<br>
<div id="addresses" style = " margin: auto; text-align:center;">
<h3 style= "border: none;color: black;padding: 10px 150px;display: inline-block;font-size: 18px;  border-radius: 10px;">My addresses</h3>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%">
<h4 style = "color:white;"><c:out value="${addressMessage}"></c:out></h4>
<c:if test="${not empty addresses}">
<c:forEach var="address" items="${addresses}">
<h5 style="color:white;"><c:out value="${address.location}"></c:out> <a href="/MyProject/profile/address/delete/${address.id}">Remove</a></h5>
</c:forEach>
</c:if>
<form action="/MyProject/profile/address" method = "get">
	<input type="text" maxlength="55" placeholder="Add address..." name="address">
	<input type="submit" value="Add" style = "color:white;background-color:red;border:0;height:25px;">
</form>
</div>
</div>
<br>
<br>
</body>
</html>