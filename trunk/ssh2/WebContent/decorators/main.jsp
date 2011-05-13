<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
<style type="text/css">
<!--
.STYLE1 {color: #333333}
-->
</style>
		<title>promoter ontology system</title>

		<link href="templatemo_style.css" rel="stylesheet" type="text/css" />
 
	</head>
	<body>
		<div id="templatemo_container">
			<div id="templatemo_header">
				<div id="templatemo_logo">
					<h1>
						&nbsp;&nbsp;&nbsp;Promoter Ontology &nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System
					</h1>
					<h2></h2>
				</div>
			
				<div class="cleaner"></div>
			</div>
			<div id="templatemo_content_area_top">
				<br />
				<br />
				<br />
				<br />


				<table width="100%" border="1" bordercolor="#339933">
					<tr>
						<td>
							<div align="center">
								<decorator:body />
								<!-- 这里的内容由引用模板的子页面来替换 -->
							</div>
						</td>
					</tr>
				</table>
			</div>
			<!-- End Of Content area top -->






			<%--<div id="templatemo_content_area_bottom">
    <!-- End of Right Section -->

  </div>
  --%>
			<!-- End Of Content area bottom -->
			<div id="templatemo_footer" >
				<h5><span class="STYLE1">
				<br /><br /><br />
					Promoter Ontology System
					<br />
					developed by Liu Yingjian ,&nbsp;&nbsp;Yu kuai
					<br />
					<a href="help.jsp" ><span class="STYLE1">Help</span></a></span></h5>
			</div>
			
		</div>

	</body>
</html>

