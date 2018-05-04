<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<c:forEach var="product" items="${applicationScope.products}">
	<h4><c:out value="${product.name}"></c:out></h4>
	<p class="w3-text-grey">
		<c:forEach var="ingredient" items="product.ingredients">
			<c:out value="${ingredient.name}"></c:out>,
		</c:forEach>
	</p>
	<h2><c:out value="${product.price}"></c:out></h2>
	<a href="pizza?id=${product.id}" ><img border="0"src="imges/eCommerce-ICON-small.png" width="20" height="20"></a>		
</c:forEach>
