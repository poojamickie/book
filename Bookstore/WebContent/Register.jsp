<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Books Store Application</title>
</head>
<body>s
	<center>
		<h1>Management</h1>
        <h2>
        	<a href="new">Register</a>

        	
        </h2>
	</center>
       <form action="RegisterDO" method="post">
       <input type="text" name="username" value="Name..." onclick="this.value=''"/><br/>
       <input type="text" name="uemail"  value="Email ID..." onclick="this.value=''"/><br/>
       <input type="password" name="password"  value="Password..." onclick="this.value=''"/><br/>
       <input type="submit" value="registerDO"/>
       </form>
</body>
</html>
