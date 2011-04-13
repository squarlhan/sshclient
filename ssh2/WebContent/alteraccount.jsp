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
<s:form action="alteraccount!alteraccount.action">
	<s:password name="currentpassword" label="Password" />
	<s:textfield name="newusername" label="New Username" />
	<s:textfield name="newsurname" label="New Surname" />
	<s:textfield name="newgivenname" label="New Givenname" />
	<s:textfield name="neworganization" label="New Organization" />
	<s:textfield name="newphone" label="New Phone" />
	<s:submit value="Submit" />
	<s:reset value="Reset" />
	<input name="submit" type="button" value="Logoff"
		onclick="window.location.href='logoff.jsp'" />

</s:form>
</body>
<script type="text/javascript" language="javascript">
	function init() {
		var curuser =
<%=request.getSession().getAttribute("USERNAME")%>
	;
		var curpass =
<%=request.getSession().getAttribute("PASSWORD")%>
	;
		if (curuser == null || curpass == null) {
			self.location = "/ssh2/index.jsp";
		}
	}
	init();
</script>
</html>