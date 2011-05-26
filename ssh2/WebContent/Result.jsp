<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GenePage</title>

</head>
<body>
<table>
	<s:iterator id="result_Taxonomy" value="resultlist_Taxonomy"
		status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><a
				href="searchGene!SearchGene.action?Taxonomy=
				<s:property value="result_Taxonomy" />"><s:property
				value="result_Taxonomy" /></a></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Tax" value="resultlist_Tax_label" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Tax" /></td>
		</tr>
	</s:iterator>
</table>

</body>
</html>