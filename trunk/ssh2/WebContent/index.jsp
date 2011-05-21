<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<base href="<%=basePath%>">

<title>s:text name="StartingPage" /</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>
<body>
<table align="center" width="100%" id="tb1">
	<s:form action="userac!login.action">
		<s:property value="tip" />
		<s:textfield name="user.username" label="username"
			onblur="checkuser()" />
		<s:div id="unameMsg" />
		<s:password name="user.password" label="password" />
		<s:radio name="type" label="role" labelposition="top"
			list="{'user' , 'admin'}" />
		<s:submit value="login" />
		<s:reset value="reset" />
	</s:form>
	<input name="submit" type="button" value="Regist"
		onclick="window.location.href='regist.jsp'" />
	<input name="submit" type="button" value="ForgetPassword"
		onclick="window.location.href='forgetPassword.jsp'" />
	<input name="submit" type="button" value="Search"
		onClick="window.location.href='search.jsp'" />
</table>
</body>
</html>
