<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PromoterPage</title>
</head>
<body>
<table>
	<s:iterator id="result_Promoter" value="resultlist_Promoter" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Promoter" /></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>