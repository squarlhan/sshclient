<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SearchPage</title>
<script type="text/javascript" language="javascript">
	function Search_gene() {
		targetForm = document.forms[0];
		targetForm.action = "search!Search_Gene.action";
	}
	function Search_promoter() {
		targetForm = document.forms[0];
		targetForm.action = "search!Search_Promoter.action";
	}
	function Search_taxonomy() {
		targetForm = document.forms[0];
		targetForm.action = "search!Search_Taxonomy.action";
	}
	function Search_keyword() {
		targetForm = document.forms[0];
		targetForm.action = "search!Search_Keyword.action";
	}
</script>
</head>
<body>
<s:form action="search!Search.action">
	<s:textfield name="Gene_name" label="Gene_Name" />
	<s:textfield name="Promoter_name" label="Promoter_Name" />
	<s:textfield name="Taxonomy_name" label="Taxonomy_Name" />
	<s:textfield name="Keywords" label="Keywords" />
	<s:submit value="Search" />
	<s:reset value="Reset" />
	<s:label value="Phaseolus vulgaris (kidney bean)" />
</s:form>
</body>
</html>