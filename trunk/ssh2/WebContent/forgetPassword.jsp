

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" language="javascript">
function sendmail() {
	targetForm = document.forms[0];
	targetForm.action = "forgetpass!sendMail";
}
function forgetpass() {
	targetForm = document.forms[0];
	targetForm.action = "forgetpass!forgetPass";
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="forgetpasswordPage" /></title>
</head>
<body>
<s:form action="actionName!methodName">
	<s:textfield name="user.username" label="username" />
	<s:textfield name="captcha" label="captcha" />
	<s:submit value="SendMail" onClick="sendmail()" />
	<s:submit value="Submit" onClick="forgetpass()" />
	<s:reset value="Reset" />
</s:form>
</body>
</html> 
