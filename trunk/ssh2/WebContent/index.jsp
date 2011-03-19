<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
    <s:form action="userac!addUser.action">
        <s:textfield name="user.username" label = "ĞÕÃû"/><br>
        <s:textfield name="mykeyword" label = "ÂÒÆß°ËÔã"/><br>
        <s:password  name="user.password" label = "ÃÜÂë"/><br>
    	<s:submit value="Ìá½»"/>
    	<s:reset  value="ÖØÖÃ"/>
    	<s:label value = "Culicinae"/>
    </s:form>
  </body>
</html>
