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
<!--<script type="text/javascript" language="javascript">
	function regist() {
		targetForm = document.forms[0];
		targetForm.action = "userac!regist.action";
	}
	function login() {
		targetForm = document.forms[0];
		targetForm.action = "userac!login.action";

	}
	function forgetPassword() {
		targetForm = document.forms[0];
		targetForm.action = "userac!forgetPassword.action";

	}
</script>-->
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
	<s:form action="userac!login.action" validate="true">
		<s:textfield name="user.username" label="username" />
		<s:password name="user.password" label="password" />
		<s:submit value="login" />
		<s:reset value="reset" />
		<s:label value="Culicinae" /> 
	</s:form>
	<input name="submit" type="button" value="Regist" onclick="window.location.href='regist.jsp'"/>
	<input name="submit" type="button" value="ForgetPassword" onclick="window.location.href='forgetpassword.jsp'"/>

</table>
</body>
</html>
