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
	<s:iterator id="result_Promoter" value="Presult" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Promoter" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Go" value="Goresult" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Go" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Tax" value="Tresult" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Tax" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_mRNA" value="mresult" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_mRNA" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Protein" value="Proresult" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Protein" /></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>