<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/component/header.jsp"%>
<title>投资模拟器[${instance.key }]-投资人</title>
</head>
<body class="container">

	<div class="page-header">
		<h1>沙盘模拟[${instance.key}]-投资人[${investorName }]</h1>
		<h3>今天是<fmt:formatDate value="${instance.currentDate }" pattern="yyyy-MM-dd" /></h3>
		<h3>在平台总投资数：${ instance.getInvestmentRecordByinvestorName(investorName).size()}笔</h3>
	</div>

	<form method="post" action="/god/addProduct.do">
		<div class="form-inline">
			<div class="form-group">
				用户名：<input type="text" class="form-control" name="investorName"
					value="${investorName }" required/> 
					<input type="submit"
					formaction="/investor/refreshName.do" class="btn btn-danger" value="换名字"/>
			</div>
		</div>
		
		<p></p>

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">已投资项目情况</h3>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<tr>
						<td></td>
						<td>产品名称</td>
						<td>投资日期</td>
						<td>投资金额</td>
						<td>开始计息日期</td>
						<td>预期收益</td>
						<td>返还日期</td>
						<td>实际收益</td>
					</tr>
					<c:forEach items="${ instance.getInvestmentRecordByinvestorName(investorName)}" var="ss">
					<c:forEach items="${ instance.activeProduct }" var="p">
					<c:if test="${ss.productName == p.name }">
					<tr class="${p.stateName }">
					    <td></td>
 						<td>${ss.productName }</td>
 						<td><fmt:formatDate value="${ss.investDate }" pattern="yyyy-MM-dd"/></td>
 						<td>${ss.amount }</td>
 						<td><fmt:formatDate value="${p.countMoneyDay }" pattern="yyyy-MM-dd"/></td>
 						<td>${ss.returnAmount }</td>
 						<td><fmt:formatDate value="${p.returnDate }" pattern="yyyy-MM-dd"/></td>
 						<td>0</td>
 					</tr> 
 					</c:if>
 					</c:forEach>
 					</c:forEach>
 					<c:forEach items="${ instance.getInvestmentRecordByinvestorName(investorName)}" var="ss">
					<c:forEach items="${ instance.historyProduct }" var="p">
					<c:if test="${ss.productName == p.name }">
					<tr class="${p.stateName }">
					    <td></td>
 						<td>${ss.productName }</td>
 						<td><fmt:formatDate value="${ss.investDate }" pattern="yyyy-MM-dd"/></td>
 						<td>${ss.amount }</td>
 						<td><fmt:formatDate value="${p.countMoneyDay }" pattern="yyyy-MM-dd"/></td>
 						<td>${ss.returnAmount }</td>
 						<td><fmt:formatDate value="${p.returnDate }" pattern="yyyy-MM-dd"/></td>
 						<td>
 						<c:if test="${p.state == 'FINISHED' }">${ss.returnAmount }</c:if>
 						<c:if test="${p.state == 'FAILED' }">0(该项目募集失败)</c:if>
 						</td>
 					</tr> 
 					</c:if>
 					</c:forEach>
 					</c:forEach>
				</table>
		</div>
        </div>
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">新投资</h3>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<tr>
						<td></td>
						<td>名称</td>
						<td>年化收益率</td>
						<td>开始筹款日期</td>
						<td>结束筹款日期</td>
						<td>筹款期限</td>
						<td>还款日期</td>
						<td>起息方式</td>
						<td>待筹金额</td>
						<td>剩余金额</td>
						<td>当前筹款状态</td>
					</tr>
					
					<c:forEach items="${instance.activeProduct }" var="p">
						<c:if test="${ !p.quota.equals(p.moneyNow) }">
						<c:if test="${ p.state != 'NOTICE' }">
						<tr >
							<td><input type="radio" name="productName"
								value="${p.name }" id="pid_${p.name }" /></td>
							<td><label for="pid_${p.name }">${p.name }</label></td>
							<td><label for="pid_${p.name }">${p.annualizedReturn }</label></td>
							<td><label for="pid_${p.name }"><fmt:formatDate
										value="${p.startDate }" pattern="yyyy-MM-dd" /></label></td>
							<td><label for="pid_${p.name }"><fmt:formatDate
										value="${p.endDate }" pattern="yyyy-MM-dd" /></label></td>
							<td><label for="pid_${p.name }">${p.deadline }</label></td>
							<td><label for="pid_${p.name }"><fmt:formatDate
										value="${p.returnDate }" pattern="yyyy-MM-dd" /></label></td>
							<td><label for="pid_${p.name }">T+${p.valueDay }</label></td>
							<td><label for="pid_${p.name }">${p.quota }</label></td>
							<td><label for="pid_${p.name }">${p.quota.subtract(p.moneyNow) }</label></td>
							<td><div class="progress" style="height:7px;">
                            <div class="progress-bar progress-bar-success" role="progressbar" 
                              aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: ${p.numberFormatData}">
                            </div>
                            </div>
                        </td>
						</tr>
						</c:if>
						</c:if>
					</c:forEach>
				</table>

				<div class="form-inline">
					<div class="form-group">
						<label for="investAmount">投资金额：</label>
						<div class="input-group">
						<div class="input-group-addon">￥</div>
						<input type="text" id="amount" title="输入金额不应该大于剩余金额" name="amount" class="form-control" onclick="checkvalue(this);"/>
						</div>
						<input type="submit" formaction="/investor/invest.do"
							class="btn btn-primary" value="提交" /><label id="tip"></label>
					</div>
				</div>
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