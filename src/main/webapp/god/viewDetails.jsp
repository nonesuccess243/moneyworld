<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/component/header.jsp" %>
<title>投资模拟器[${instance.key }]</title>
</head>
<body class="container">
<div class="page-header">
		<h1>已成功项目投资详情界面</h1>
	</div>

<div class="panel panel-default">
<div class="panel-body">
		<h3 class="panel-title">已结算</h3>
			<table class="table">
				<tr>
				    <td>项目名称</td>
					<td>投资人姓名</td>
				    <td>投资额</td>
					<td>投资人收益</td>
					<td>平台收益</td>
					<td>投资日期</td>
					<td>开始筹款日期</td>
					<td>结束筹款日期</td>
					<td>结算日期</td>
				</tr>
				<c:forEach items="${instance.historyProduct }" var="p">
				<c:if test="${p.state == 'FINISHED' }">
				<c:forEach items="${ instance.getInvestmentRecordByproductName(p.name)}" var="ss">
					<!--此处加判断，判断是否为不活跃，判断条件当前日期不在结束筹款日期（包含）前，并排除钱已筹齐的情况-->
					<tr>
					    <td>${p.name }</td>
						<td>${ss.investorName } </td>
 						<td>${ss.amount }</td>
 						<td>${ss.returnAmount }</td>
 						<td>${ss.returnAmountTwo}</td>
 						<td><fmt:formatDate value="${ss.investDate }" pattern="yyyy-MM-dd"/></td>
 						<td><fmt:formatDate value="${p.startDate }" pattern="yyyy-MM-dd"/></td>
 						<td><fmt:formatDate value="${p.endDate }" pattern="yyyy-MM-dd"/></td>
 						<td><fmt:formatDate value="${p.returnDate }" pattern="yyyy-MM-dd"/></td>
					</tr>
				</c:forEach>
				 </c:if>
				 </c:forEach>
			</table>
		</div>
	</div>
</body>
</html>