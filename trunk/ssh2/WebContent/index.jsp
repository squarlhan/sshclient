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
<script type="text/javascript" language="javascript">
	var xmlHttp;
	var XMLHttpRequestObject = false;
	var result = false;
	function checkuser() {
		var uname = document.all.user.username.value;
		if (uname == "") {
			document.getElementById("unameMsg").innerHTML = "<font color='red'>用户名不能为空！</font>";
			return false;
		} else if (uname.length<4||uname.length>25) {
			document.getElementById("unameMsg").innerHTML = "<font color='red'>用户名4-25字符，请重新输入！</font>";
			return false;
		} else {
			if (window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//其他浏览器实例				   
			}
			var uri = "userac!login?user=" + uname;
			uri = encodeURI(uri);
			xmlHttp.open("post", uri, true);
			xmlHttp.onreadystatechange = function() {//回调函数判断当前状态是否是响应状态
				if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					//res =XMLHttpReq.responseXML.getElementsByTagName("msg")[0].firstChild.data; 
					alert(xmlHttp.responseText);
					if (xmlHttp.responseText == "true") {
						document.all.unameMsg.innerHTML = "<font color='red'>该用户名已被使用，请您重新选择用户名！</font>";
						result = false;

					} else {
						document.all.unameMsg.innerHTML = "<font color='red'>恭喜您，该用户名可用!</font>";
						result = true;
					}
				}
			}
			xmlHttp.send(null);

		}

	}
</script>
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
