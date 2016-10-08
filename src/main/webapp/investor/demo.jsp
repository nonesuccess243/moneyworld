<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/component/header.jsp"%>
<title>免费技术服务——区级工作台</title>
</head>
<body class="container">

	<div class="page-header">
		<h1>免费技术服务——区级工作台</h1>
		<h3>今天是2016年4月12日</h3> 
		<h3>本月完成检查数目：245</h3>
	</div>

	<form method="post" action="/god/addProduct.do">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">街镇工作情况统计</h3>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<tr>
						<td></td>
						<td>申请</td>
						<td>通过</td>
						<td>手术类型</td>
						<td>完成检查</td>
						<td>随访率</td>
					</tr>
					<tr>
						<td>塘沽街</td>
						<td></td>
						<td></td>
						<td></td> 
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>汉沽街</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>大港街</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>……</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>……</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
		</div>
        </div>  
	</form>
</body>
<script>
function checkvalue(obj)
{ var oTip = document.getElementById("tip");
  oTip.innerHTML =obj.title;
}
</script>
</html>