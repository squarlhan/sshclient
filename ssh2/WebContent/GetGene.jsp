<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Choose Taxonomy</title>
</head>
<body>
<table>
<s:iterator id="result_Gene" value="resultlist_Gene" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><a href="geneDetails!GeneDetails.action?Gene=<s:property value="result_Gene" />"><s:property value="result_Gene" /></a></td>
		
		</tr>
	</s:iterator>
	<s:iterator id="result_Gene_name" value="resultlist_Gene_name" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Gene_name" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Gene_id" value="resultlist_Gene_id" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Gene_id" /></td>
		</tr>
	</s:iterator>

</table>
</body>
</html>