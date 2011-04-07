<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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

<title>success</title>

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
<div style="color: green;">success</div>
<br>
<table align="center" width="100%" id="tb">
	<tr bgcolor="#4A708B">
		<th>IDIDID</th>
	</tr>
</table>

<table align="center" width="100%" id="tb1">
	<tr bgcolor="#4A708B">
		<th>USERNAME</th>
		<th>SURNAME</th>
	</tr>
	<s:iterator id="users" value="UserService.getUserlist()"
		status="index1">
		<tr align="center"
			bgcolor="<s:if test="#index1.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="user.username" /></td>
			<td><s:property value="user.password" /></td>
		</tr>
	</s:iterator>
	
</table>
<input name="submit" type="button" value="Alter Password" onclick="window.location.href='alterpass.jsp'"/>
</body>
</html>
