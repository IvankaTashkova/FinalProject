<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="/MyProject/css/css.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>DominosPizza | Order</title>
</head>
<body style = "background-image: linear-gradient(white 15%, #ff4040 100%);">
<!-- Navbar (sit on top) -->
<div class="w3-top" style = "font-family:Arial">
  <div class="w3-bar w3-white w3-padding w3-card">
    <a href="home" ><img border="0"src="images/home-icon.png" width="40" height="40"></a>
    <!-- Right-sided navbar links. Hide them on small screens -->
    <div class="w3-right w3-hide-small" style = "font-family:Arial">
      <a href="profile"><img src = "images/user-icon.png" width = "25" height = "25" style = "margin-top:0px; margin-bottom:0;"></a>
      <a href="#menu" class="w3-bar-item w3-button">Menu</a>
      <form action = "/MyProject/logout" method = "post"><input type= "submit" class="w3-bar-item w3-button" value = "Logout"></form>
    </div>
  </div> 
</div>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%;margin-top:150px;">
<table style="margin:auto;">
	<tr>
		<th>Product</th>
		<th>Quantity</th>
		<th>Price</th>
	</tr>
	<c:forEach var = "product" items = "${sessionScope.productsInCart}">
		<tr>
			<td><c:out value="${product.key.name}"></c:out> </td>
			<td><c:out value="${product.value}"></c:out></td>
			<td><c:out value="${product.key.price*product.value}"></c:out></td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="3">Total price: <c:out value="${totalPrice }"></c:out> </td>
	</tr>
</table>
</div>
<form>
<br>
<br>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%">
<p>Choose address:</p>
	<p>Select from you addresses:</p>

	<p>or</p>
	<p>Add new address:</p>
	<input type="text" name="address" placeholder="address">
</div>
<br>
<br>
<div style= "margin:auto;border:1px solid red;padding:10px;text-align:center;width:50%">
	<p>You can take you order from one of our restaurants!</p>
 	<input type="checkbox" name="fromrestaurant" value="">Take from restaurant
 	<p>Choose one:</p>
 	<div id="map" style = "height: 500px; width : 500px; margin:auto; margin-bottom:50px; ">
    <script>
      function initMap() {
    	  var location = {lat: 42.697, lng: 23.324};
       
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 11.5,
          center: location
        });
        
        function addMarker(properties){
        	var marker = new google.maps.Marker({
            	position:properties.coordinates,
            	map:map
        	});
            if(properties.content){
            	var infoWindow = new google.maps.InfoWindow({
            		content:properties.content
            	});
            			
      			marker.addListener('click',function(){
      				infoWindow.open(map,marker);
      			});
            }
        }
        
        addMarker({
        		coordinates:{lat:42.649,lng:23.408},
        		content:'<h3>Domino\'s</h3><h6>48 prof. Tsvetan Lazarov Boulevard, 1582 Dryjba 2, София</h6>'
        });
        
        addMarker({
    		coordinates:{lat:42.653,lng:23.346},
    		content:'<h3>Domino\'s</h3><h6>4 Yordan Yosifov Street, 1700 Stydentski kompleks, Sofia</h6>'
   		 });
       
        addMarker({
    		coordinates:{lat:42.678,lng:23.364},
    		content:'<h3>Domino\'s</h3><h6> 1113 Shiptchenski prohod Boulevard, 1113 Geo Milev, Sofia</h6>'
   		 });
        
        addMarker({
    		coordinates:{lat:42.672,lng:23.285},
    		content:'<h3>Domino\'s</h3><h6>8 gen. Stefan Toshev Street, 1680 Borovo, Sofia</h6>'
   		 });
        
        addMarker({
    		coordinates:{lat:42.680,lng:23.356},
    		content:'<h3>Domino\'s</h3><h6>6 Alexander Jendov Street, 1113 Geo Milev, Sofia</h6>'
   		 });
        
        addMarker({
    		coordinates:{lat:42.707,lng:23.288},
    		content:'<h3>Domino\'s</h3><h6>271 Tsar Simeon Street, 1309 Ilinden, Sofia</h6>'
   		 });
        
        addMarker({
    		coordinates:{lat:42.697,lng:23.317},
    		content:'<h3>Domino\'s</h3><h6> 41 Alexander Stamboliiski Boulevard, 1532 Sofia</h6>'
   		 });
                
        addMarker({
    		coordinates:{lat:42.636,lng:23.369},
    		content:'<h3>Domino\'s</h3><h6>78 Alexander Malinov Boulevard, 1712 Amerikanski kolej, Sofia</h6>'
   		 });
        
        addMarker({
    		coordinates:{lat:42.674,lng:23.307},
    		content:'<h3>Domino\'s</h3><h6>Biala Tcherkva Street, 1407 Sofia</h6>'
   		 });
        
        addMarker({
    		coordinates:{lat:42.665,lng:23.319},
    		content:'<h3>Domino\'s</h3><h6>6 Nikola Vaptsarov Boulevard, 1407 Lozenets, Sofia</h6>'
   		 });
        
        addMarker({
    		coordinates:{lat:42.703,lng:23.356},
    		content:'<h3>Domino\'s</h3><h6>Syhata reka 12-E, 1505 Sofia</h6>'
   		 });
        
        addMarker({
    		coordinates:{lat:42.734,lng:23.294},
    		content:'<h3>Domino\'s</h3><h6>174 Lomsko shose, 1231 Nadejda 4, Sofia</h6>'
   		 });

        addMarker({
    		coordinates:{lat:42.661,lng:23.264},
    		content:'<h3>Domino\'s</h3><h6>38 Alexander Pyshkin Street, 1000 Bukston, Sofia</h6>'
   		 });

      }
      
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key= AIzaSyB9OVx6nYiXHnqLDdCxPqx0TYenbqCGv2o&callback=initMap"
    async defer></script>
    </div>
	<a href="" style = "color :white;">Make Order</a>
</div>
<br>
<br>
</form>
</body>
</html>