<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="registPage" /></title>
</head>
<body>
<s:form action="regist!addUser.action" validate="true">
	<s:textfield name="user.username" label="Username" />
	<s:textfield name="user.surname" label="Surname" />
	<s:textfield name="user.givenname" label="Givenname"/>
	<s:password name="user.password" label="Password" />
	<s:password name="repassword" label="Confirm" />
	<s:textfield name="user.organization" label="Organization" />
	<s:textfield name="user.phone" label="Phone" />
	<s:submit value="Submit" />
	<s:reset value="Reset" />
</s:form>
</body>
</html>