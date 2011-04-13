<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" language="javascript">
  function init(){
    var curuser = <%=request.getSession().getAttribute("USERNAME")%>;
    var curpass = <%=request.getSession().getAttribute("PASSWORD")%>;
    if(curuser == null||curpass == null){
    	tip="Please login first!";
    	self.location = "/ssh2/index.jsp";
    }
  }
  init();
</script>
<base href="<%=basePath%>">

<title>ERROR</title>

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
<div style="color: red;"><s:property value="tip" /></div>

<div style="color: red;">Please input again!</div>
<br>

<!-- <table align="center" width="100%" id="tb1">
	<s:form action="alter">
		<s:submit value="Alter password" />
		<s:property value="user.username" />
		<s:property value="user.password" />
	</s:form>

	<tr bgcolor="#4A708B">
		<th>USERNAME</th>
		<th>SURNAME</th>
	</tr>
	<s:iterator id="users" value="UserServiceImpl.getUserlist()"
		status="index1">

		<tr align="center"
			bgcolor="<s:if test="#index1.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="username" /></td>
			<td><s:property value="password" /></td>
		</tr>
	</s:iterator>
</table> -->

<input name="submit" type="button" value="Login"
		onclick="window.location.href='index.jsp'" />
<input name="submit" type="button" value="Regist"
		onclick="window.location.href='regist.jsp'" />
<input name="submit" type="button" value="Alter Password"
	onclick="window.location.href='alterpass.jsp'" />
<input name="submit" type="button" value="Alter Account"
	onclick="window.location.href='alteraccount.jsp'" />
<input name="submit" type="button" value="Log off"
	onclick="window.location.href='logoff.jsp'" />


</body>
</html>
