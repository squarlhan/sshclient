<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<s:form action="alter!alterpass.action">
	<s:password name="currentpassword" label="current password" />
	<s:password name="newpassword" label="new password" />
	<s:password name="renewpassword" label="confirm new password" />
	<s:submit value="Submit" />

</s:form>
<table>
	<tr align="center">
		<td><label><%=request.getSession().getAttribute("USERNAME")%></label>
		</td>
		<td><label><%=request.getSession().getAttribute("PASSWORD")%></label>
		</td>
	</tr>
	
</table>


</body>
</html>