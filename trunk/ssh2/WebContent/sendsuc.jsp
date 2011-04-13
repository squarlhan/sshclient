<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
</head>
<body>
The E-mail has been sent successfully!Please check your E-mail to get the captcha.
<input name="submit" type="button" value="return to the login page" onclick="window.location.href='index.jsp'"/>
<tr align="center">
		<td><label><%=request.getSession().getAttribute("USERNAME")%></label>
		</td>
		<td><label><%=request.getSession().getAttribute("PASSWORD")%></label>
		</td>
	</tr>

</body>
</html>