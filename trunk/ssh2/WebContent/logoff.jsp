<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" language="javascript">
  function init(){
    var curuser = <%=request.getSession().getAttribute("USERNAME")%>;
    var curpass = <%=request.getSession().getAttribute("PASSWORD")%>;
    if(curuser == null||curpass == null){
    	tip="Please login first!";
    	self.location = "/ssh2/index.jsp";
    }
  }
  init();
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<s:form action="logoff!logoff.action" >
	<s:label value="Are your sure to log off?" />
	<s:label value="Click YES,you will return to the login page"/>
	<s:submit value="YES" />
</s:form>
<input name="submit" type="button" value="NO" onclick="window.location.href='success.jsp'"/>


</body>
</html>