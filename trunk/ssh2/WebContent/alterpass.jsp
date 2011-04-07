<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String username=request.getSession().getAttribute("USERNAME");
String password=request.getSession().getAttribute("PASSWORD");
%>
<s:form action="alter!alterpass.action">
<s:textfield name="user.username" value="username"/>
<s:textfield name="user.password" value="password"/>
	<s:password name="currentpassword" label="current password" />
	<s:password name="newpassword" label="new password" />
	<s:password name="renewpassword" label="confirm new password" />
	<s:submit value="Submit" />

</s:form>

</body>
</html>