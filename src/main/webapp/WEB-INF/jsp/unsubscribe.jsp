<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<div>
        <img src="${pageContext.request.contextPath}/img/logo.jpeg" style="width:300px;" />
    </div>
<div class="jumbotron text-center">
 
  <h1 class="display-3">Thank You!</h1>
  <p class="lead"><strong>Hello ${username}, You have remove from '${companyName}' Address Book</strong> You will not received our Publish Update.</p>
  <hr>
  <p>
  </p>
</div>
</body>
</html>