
<%@page import="com.example.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/css.css">
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
    padding-top: 200px;
    opacity:0.75;
}

.sidenav button {
	background-color:red;
	border:0;
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 20px;
    color: white;
    display: block;
    opacity:0.75;
}

.onclick button {
	background-color:white;
	border:0;
    padding: 6px 8px 6px 16px;
    text-decoration: none;
    font-size: 20px;
    color: red;
    display: block;
    opacity:0.75;
}

.sidenav button:hover {
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
<body>

<div class="sidenav">
  <button href = "#profile">Edit profile</button>
 <button href = "#orders">View Orders<button>
 <button href = "#cart">Shopping Cart<button>
 <button href = "addresses">My addresses<button>
</div>
<div class="w3-top" style = "font-family:Arial">
  <div class="w3-bar w3-white w3-padding w3-card">
    <a href="home" ><img border="0"src="images/home-icon.png" width="40" height="40"></a>
    <!-- Right-sided navbar links. Hide them on small screens -->
    <div class="w3-right w3-hide-small" style = "font-family:Arial">
      <a href="profile"><img src = "images/user-icon.png" width = "25" height = "25" style = "margin-top:0px; margin-bottom:0;"></a>
      <a href="#menu" class="w3-bar-item w3-button">Menu</a>
      <form action = "logout" method = "post"><input type= "submit" class="w3-bar-item w3-button" value = "Logout"></form>
    </div>
  </div> 
</div>
<div id="#profile" style = " margin: auto; text-align:center;">
<p style = "color:red;"><c:out value=" ${info}"></c:out></p>
	<form action = "profile/update/${user.id}" method = "post">
	<img src="images/user-icon.png" width = "80" style = "margin-top:5px;opacity:1;possition:center;display: block;margin-top:150px;margin-left: auto; margin-right: auto;"><h3 style= "border: none;color: black;padding: 10px 150px;display: inline-block;font-size: 18px;  border-radius: 10px;">Profile</h3>
	<%  User user  = (User) session.getAttribute("user"); %>
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


<div id="view_orders" style = " margin: auto; text-align:center;">

</div>
</body>
</html>