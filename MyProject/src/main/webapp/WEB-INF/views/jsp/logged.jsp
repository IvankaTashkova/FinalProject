<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Domino's Pizza | Home</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/css.css">
<style>
body {font-family: "Monotype corsiva", Georgia, Serif;}
h1,h2,h3,h4,h5,h6 {
    font-family: "Arial";
    letter-spacing:1px;
}
</style>
<body>

<!-- Navbar (sit on top) -->
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

<header class="w3-display-container w3-content w3-wide" style="max-width:1600px;min-width:500px" id="home">
  <img class="w3-image" src="images/image1.jpg" alt="Hamburger Catering" width="1600" height="800">
  <div class="w3-display-bottomleft w3-padding-large w3-opacity">
  
      <h1 style = "color:white;padding-top:50px">Domino's</h1><br>
      <h5 style = "color:white">We love pizza</h5>
  </div>
</header>

<div class="w3-content" style="max-width:1100px">

  <div class="w3-row w3-padding-64" id="menu">
    <div class="w3-col l6 w3-padding-large">
    
    </div>
    <div class="w3-col l6 w3-padding-large">
      <img src="images/image-menu.jpg" class="w3-round w3-image w3-opacity-min" alt="Menu" style="width:100%">
    </div>
  </div>

  <hr>
   <div class="w3-container w3-padding-64" id="contact">
    <h1>Contact</h1><br>
   <h3><img src ="images/phone.png" width="20" height="20"> 070012525</h3>
    <h3>Find us on</h3>
    <p><a href = "https://www.facebook.com/DominosBulgaria"><img src ="images/fb.png" width="30" height="30"></a>
     <a href = "https://www.instagram.com/dominos_bg/"><img src ="images/ig.png" width="30" height="30"></a>
      <a href = "https://www.youtube.com/channel/UCUTBiIm_r68gSa1JY2tgDLg"><img src ="images/yt.png" width="40" height="30"></a></p>
	<p></p>
  </div>
<!-- End page content -->
</div>

<!-- Footer -->
<footer class="w3-center w3-light-grey w3-padding-32">

</footer>

</body>
</html>
