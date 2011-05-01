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

<title>success</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" language="javascript">
	function init() {
		var curuser =
<%=request.getSession().getAttribute("USERNAME")%>
	;
		var curpass =
<%=request.getSession().getAttribute("PASSWORD")%>
	;
		if (curuser == null || curpass == null) {
			tip = "Please login first!";
			self.location = "/ssh2/index.jsp";
		}
	}
	init();
	
	function Search_gene() {
		targetForm = document.forms[0];
		targetForm.action = "search!Search_Gene";
	}
	function Search_promoter() {
		targetForm = document.forms[0];
		targetForm.action = "search!Search_Promoter";
	}
	function Search_taxonomy() {
		targetForm = document.forms[0];
		targetForm.action = "search!Search_Taxonomy";
	}
	function Search_keyword() {
		targetForm = document.forms[0];
		targetForm.action = "search!Search_Keyword";
	}

	function init(){
	  var curuser = <%=request.getSession().getAttribute("USERNAME")%>;
	  var curpass = <%=request.getSession().getAttribute("PASSWORD")%>;
	  if(curuser == null||curpass == null){
		  tip="Please login first!";
	  	self.location = "/ssh2/index.jsp";
	  }
	}
</script>

</head>

<body>

<tr align="center">
	<td><label><%=request.getSession().getAttribute("USERNAME")%></label>
	</td>
	<td><label><%=request.getSession().getAttribute("PASSWORD")%></label>
	</td>
</tr>
<div style="color: green;"><s:property value="tip" /></div>

<br>
<!--  <table align="center" width="100%" id="tb">
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

</table>-->
<input name="submit" type="button" value="Alter Password"
	onclick="window.location.href='alterpass.jsp'" />
<input name="submit" type="button" value="Alter Account"
	onclick="window.location.href='alteraccount.jsp'" />
<input name="submit" type="button" value="Log off"
	onclick="window.location.href='logoff.jsp'" />

<s:form action="actionName!methodName">
	<s:textfield name="Gene_name" label="Gene_Name" />
	<s:submit value="Search Gene" onClick="Search_gene()" />
	<s:textfield name="Promoter_name" label="Promoter_Name" />
	<s:submit value="Search_Promoter" onClick="Search_promoter()" />
	<s:textfield name="Taxonomy_name" label="Taxonomy_Name"/>
	<s:submit value="Search_Taxonomy" onClick="Search_taxonomy()"/>
	<s:textfield name="Keyword" label="Keyword"/>
	<s:submit value="Search_Keyword" onClick="Search_keyword()"/>
	<s:reset value="Reset" />
	<s:label value="Culicinae" />
</s:form>

</body>
</html>
