<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Domino's Pizza | Login</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css.css">
<body>
<div class="w3-bar w3-white w3-padding w3-card">
    <a href="html.html" ><img border="0"src="home-icon.png" width="40" height="40"></a>
 </div> 
<div class = "login">
&{error}
	<form action="login" method = "post">
		
		<h3 style= "background-color: blue; margin-top:50px;margin-left:-20px;opacity:0.80; border: none;color: white;padding: 5px 150px;text-align: center;display: inline-block;font-size: 18px;  border-radius: 15px;">Log in</h3>
		  <p><input type="text" placeholder="username" required name="username" style="opacity:0.80;height:40px;border: none;border-bottom: 2px solid blue;"></p>
		  <p><input type="password" placeholder="password" required name="password" style="opacity:0.80;height:40px;border: none;border-bottom: 2px solid blue;"></p>
		  <p><input type= "submit" value = "Log in" style = " opacity:0.80; background-color: blue;border: none;color: white; font-size: 18px;  border-radius: 15px; height:35px;"></p>
		  <p><a href = "signinpage.html" style = "color : white;">I don't have registration! </a></p>
		</form>
	</div>
</body>
</html>