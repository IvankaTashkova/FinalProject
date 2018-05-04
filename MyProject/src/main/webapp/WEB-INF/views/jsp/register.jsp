<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Domino's Pizza | Sing in</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/css.css">
<body>
<div class="w3-top" style = "font-family:Arial">
  <div class="w3-bar w3-white w3-padding w3-card">
    <a href="home" ><img border="0"src="images/home-icon.png" width="40" height="40"></a>
  </div> 
</div>

<header style="max-width:1600px;min-width:500px" id="home">
  <div style = "position:fixed; left:40%; bottom:0;">
	<div class = "signin-container" style= "text-align:center">
		<form action="register" method="post" >
		<p style="color:red;"><c:out value="${info}"></c:out></p>
		<h3  style= "background-color: red;border: none;color: white;padding: 5px 150px;text-align: center;display: inline-block;font-size: 18px;border-radius:10px;opacity:0.85;">Sign in</h3>
		<p><input style = "padding:5px;margin-left:25%;display:block;border:none;border-bottom:1px solid red;width:200px;height:40px;text-align:center;" type="text" placeholder="first name" required name="firstname"></p>
		  <p><input style = "padding:5px;margin-left:25%;display:block;border:none;border-bottom:1px solid red;width:200px;height:40px;text-align:center;" type="text" placeholder="last name" required name="lastname"></p>
		  <p><input style = "padding:5px; margin-left:25%;display:block;border:none;border-bottom:1px solid red;width:200px;height:40px;text-align:center;" type="text" placeholder="username" required name="username"></p>
		  <p><input style = "padding:5px;margin-left:25%;display:block;border:none;border-bottom:1px solid red;width:200px;height:40px;text-align:center;" type="email" placeholder="email" required name="email"></p>
		   <p><input style = "padding:5px;margin-left:25%;display:block;border:none;border-bottom:1px solid red;width:200px;height:40px;text-align:center;" type="password" placeholder="password" required name="password"></p>
		  <p><input style = "padding:5px;margin-left:25%;display:block;border:none;border-bottom:1px solid red;width:200px;height:40px;text-align:center;" type="password" placeholder="confirm password" required name="confirmpassword"></p>
		  <p><input style = "padding:5px;margin-left:25%;display:block;border:none;border-bottom:1px solid red;width:200px;height:40px;text-align:center;" type="text" placeholder="phone number" required name="phoneNumber"></p>
		  <p><button style = " background-color: red;border: none;color: white;padding: 10px 32px;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;  border-radius: 10px; opacity:0.85;" type="submit">Sign in</button></p>
		</form>
		<a href="login" style = "color : red;">I already have registration!</a>
	</div>
  </div>
</header>
</body>
</html>