<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Error</title>
</head>
<style>
body{
background-image: url('/MyProject/images/error.png');
background-repeat:no-repeat;
background-size:cover;
}
</style>
<body>
		<h1>We are sorry,something went wrong!</h1>
		<h2><c:out value="${requestScope.exception.message}"></c:out></h2>
		<p>Back to <a href="/MyProject/">home page!</a></p>
</body>
</html>