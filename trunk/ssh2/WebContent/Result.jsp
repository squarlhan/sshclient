<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
	<s:iterator id="result_Gene" value="resultlist_Gene" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Gene" /></td>
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
	<s:iterator id="result_Promoter" value="resultlist_Promoter" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Promoter" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Promoter_name" value="resultlist_Promoter_name" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Promoter_name" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Promoter_id" value="resultlist_Promoter_id" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Promoter_id" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Taxonomy" value="resultlist_Taxonomy" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Taxonomy" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Tax_label" value="resultlist_Tax_label" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Tax_label" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Tax_id" value="resultlist_Tax_id" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Tax_id" /></td>
		</tr>
	</s:iterator>	
	<s:iterator id="result_Protein" value="resultlist_Protein" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Protein" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Protein_name" value="resultlist_Protein_name" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Protein_name" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Protein_id" value="resultlist_Protein_id" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Protein_id" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_nRNA" value="resultlist_mRNA" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_mRNA" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_nRNA_name" value="resultlist_mRNA_name" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_mRNA_name" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_nRNA_id" value="resultlist_mRNA_id" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_mRNA_id" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Keyword" value="resultlist_Keyword" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Keyword" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Keyword_keyword" value="resultlist_Keyword_keyword" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Keyword_keyword" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Go" value="resultlist_Go" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Go" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Go_item" value="resultlist_Go_item" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Go_item" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Homology" value="resultlist_Homology" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Homology" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Homology_name" value="resultlist_Homology_name" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Homology_name" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Reference" value="resultlist_Reference" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Reference" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Reference" value="resultlist_Reference" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Reference" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Reference_id" value="resultlist_Reference_id" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Reference_id" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Reference_author" value="resultlist_Reference_author" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Reference_author" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Reference_title" value="resultlist_Reference_title" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Reference_title" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Reference_location" value="resultlist_Reference_location" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Reference_location" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Resource" value="resultlist_Resource" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Resource" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Resource_id" value="resultlist_Resource_id" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Resource_id" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Resource_name" value="resultlist_Resource_name" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Resource_name" /></td>
		</tr>
	</s:iterator>
	<s:iterator id="result_Resource_link" value="resultlist_Resource_link" status="index">
		<tr align="center"
			bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
			<td><s:property value="result_Resource_link" /></td>
		</tr>
	</s:iterator>

</table>

</body>
</html>