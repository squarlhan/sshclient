<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="utf-8"%>
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
<title>Help Documents</title>
</head>
<body>
<table><tr><td  align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Promoter is involved in gene transcription and regulation of specific DNA sequences. Contains the core promoter region and regulatory regions. Basic core promoter region as the level of transcription regulatory regions can be made on the responses of different environmental conditions, the expression of genes to make the necessary adjustments. 
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To find information about a particular field of study, biologists tend to spend a lot of time, even worse, different biological databases may use different terminology, like some of the same dialect, which allows information to find more trouble in particular, is to make the machine look of nowhere. Promoter body solve this problem is to initiate a project. But now there are a lot of the promoter data set or database, each data set are different methods of data collection in different data sets and conflicts inevitably some redundancy, which makes the information collection and processing are often problems. 
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The purpose of this project is to synthesize all the data sets to provide a unified, non-redundant database, the database is not the way to the organization ontology. This approach can effectively improve query recall and precision, so as to achieve efficient and rapid query purposes.
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If you need anything to help,please send email to us:
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Liu Yingjian :kansania@gmail.com;Yu Kuai:ykwolf@163.com.
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="index.jsp" ><span class="STYLE1">Back to login</span></a>
</td></tr></table>


</body>
</html>