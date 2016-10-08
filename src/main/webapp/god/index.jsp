<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/component/header.jsp"%>
<title>投资模拟器[${instance.key }]-管理员</title>
</head>
<body class="container">

	<div class="page-header">
		<h1>投资模拟器[${instance.key }]-管理员&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h1>
		<span><a href="/god/show.do">刷新</a>  <a href="/god/prepare.jsp">重新开始</a></span>
	</div>
	
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">摘要信息</h3>
				</div>
				<div class="panel-body">
					<div class="list-group">
						<p class="list-group-item">模拟编号：${instance.key }</p>
						<p class="list-group-item">
							开始日期：
							<fmt:formatDate value="${instance.startDate }"
								pattern="yyyy-MM-dd" />
						</p>
						<p class="list-group-item">
							当前日期：
							<fmt:formatDate value="${instance.currentDate }"
								pattern="yyyy-MM-dd" />
						</p>
						<p class="list-group-item">账户余额：${instance.totalBalance }</p>

						<p class="list-group-item">项目总数：${instance.activeProduct.size() + instance.historyProduct.size() }</p>
						<p class="list-group-item">平台盈利合计：${instance.totalProfit }</p>
						<p class="list-group-item">投资者盈利合计：${instance.totalInvestorInterest }</p>
						<p class="list-group-item">应收未收合计：${instance.receivableAccountReceivable }</p>
						<p class="list-group-item">应付未付合计：${instance.payableWithout }</p>
					</div>
					<form class="form-inline" action="/god/go.do" method="post">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">前进</div>
								<input type="text" class="form-control" id="day" name="day"
									value="1">
								<div class="input-group-addon">天</div>
							</div>
						</div>
						<button type="submit" class="btn btn-primary">前进</button>
					</form>
				</div>
				<div></div>
				<div class="panel-heading">
					<h3 class="panel-title">每日简报</h3>
				</div>
				<div class="panel-body">
					<div class="list-group">
						<p class="list-group-item">模拟编号：${instance.key }</p>
						<p class="list-group-item">
							开始日期：
							<fmt:formatDate value="${instance.startDate }"
								pattern="yyyy-MM-dd" />
						</p>
						<p class="list-group-item">
							当前日期：
							<fmt:formatDate value="${instance.currentDate }"
								pattern="yyyy-MM-dd" />
						</p>
						<p class="list-group-item">账户余额：${instance.totalBalance }</p>

						<p class="list-group-item">项目总数：${instance.activeProduct.size() + instance.historyProduct.size() }</p>
						<p class="list-group-item">平台盈利合计：${instance.totalProfit }</p>
						<p class="list-group-item">投资者盈利合计：${instance.totalInvestorInterest }</p>
						<p class="list-group-item">应收未收合计：${instance.receivableAccountReceivable }</p>
						<p class="list-group-item">应付未付合计：${instance.payableWithout }</p>
					</div>
					<form class="form-inline" action="/god/go.do" method="post">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">前进</div>
								<input type="text" class="form-control" id="day" name="day"
									value="1">
								<div class="input-group-addon">天</div>
							</div>
						</div>
						<button type="submit" class="btn btn-primary">前进</button>
					</form>
				</div>
				
			</div>
		</div>

       <div>
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">添加理财产品</h3>
				</div>
				<div class="panel-body">
					<form action="/god/addProduct.do" method="post">

						<div class="form-group">
							<label for="name">名称：</label> <input type="text" name="name"
								class="form-control" id="name" value=""
								placeholder="如果留空则随机分配一个名字" />
						</div>

						<div class="form-group">
							<label for="quota">项目总额：</label>
							<div class="input-group">
								<div class="input-group-addon">￥</div>
								<input type="text" id="quota" name="quota" value="100000"
									class="form-control" />
							</div>
						</div>

						<div class="form-group">
							<label for="annualizedReturn">年化收益率：</label>
							<div class="input-group">
								<input type="text" id="annualizedReturn" name="annualizedReturn"
									value="8" class="form-control" />
								<div class="input-group-addon">%</div>
							</div>
						</div>

                        <div class="form-group">
							<label for="interest">借款人利息：</label>
							<div class="input-group">
								<input type="text" id="interest" name="interest"
									value="10" class="form-control" />
								<div class="input-group-addon">%</div>
							</div>
						</div>

						<div class="form-group">
							<label for="startDate">开始筹款日期(yyyy-MM-dd)：</label> <input
								type="date" name="startDate" id="startDate" class="form-control"
								value="<fmt:formatDate value="${instance.currentDate }" pattern="yyyy-MM-dd" />" />
						</div>

						<div class="form-group">
							<label for="endDate">结束筹款日期(yyyy-MM-dd)：</label> <input
								type="date" name="endDate" id="endDate" class="form-control"
								value="<fmt:formatDate value="${instance.thirtyDaysAfter }" pattern="yyyy-MM-dd" />" />
						</div>

						<div class="form-group">
							<label for="returnDate">还款日期(yyyy-MM-dd)：</label> <input
								type="date" name="returnDate" id="returnDate"
								class="form-control"
								value="<fmt:formatDate value="${instance.getDaysAfter(40) }" pattern="yyyy-MM-dd" />" />
						</div>

						<div class="form-group">
							<label for="valueDay">起息方式：</label>
							<div class="input-group">
								<div class="input-group-addon">T+</div>
								<input type="text" id="valueDay" name="valueDay" value="1"
									class="form-control" />
							</div>
						</div>

						<div class="form-group">
							<label>收益方式：</label> <input type="text" class="form-control"
								value="一次性付清本息" readonly />
						</div>

                        <div class="form-group">
							<label for="badMoney">抵押物价值：</label>
							<div class="input-group">
								<div class="input-group-addon">￥</div>
								<input type="text" id="badMoney" name="badMoney" value="80000"
									class="form-control" />
							</div>
						</div>

                        <div id="testD" >
							<div>
								<input type="hidden" id="moneyNow" name="moneyNow" value="1000000"/>
							</div>
                        </div>
                        <button type="submit" class="btn btn-primary">添加</button>

					</form>
				</div>
			</div>

		</div>
	</div>
</div>



<form method="post">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">已有理财产品</h3>
		</div>
		<div class="panel-body">
		<h3 class="panel-title">投资中</h3>
			<table class="table table-hover">
				<tr>
				    <td></td>
					<td>名称</td>
					<td>年化收益率</td>
					<td>借款人利息</td>
					<td>开始筹款日期</td>
					<td>结束筹款日期</td>
					<td>筹款期限</td>
					<td>还款日期</td>
					<td>起息方式</td>
					<td>已筹金额</td>
					<td>金额</td>
					<td>抵押物价值</td>
					<td>当前筹款状态</td>
				</tr>
				<c:forEach items="${instance.activeProduct }" var="p">
				<!--此处加判断，判断是否为活跃，判断条件当前日期在结束日期之前（不包含结束日期当天）且钱没筹齐-->
					<tr class="${p.stateName }">
					    <c:if test="${p.state == 'COMPLIANCE' }">
							<td><input type="radio" name="productName"
								value="${p.name }" id="pid_${p.name }" /></td>
						</c:if>
						<c:if test="${p.state != 'COMPLIANCE' }">
						    <td><input type="radio" name="productName"
								value="${p.name }" id="pid_${p.name }" disabled="disabled" /></td>
						</c:if>
						<td><label for="pid_${p.name }">${p.name }</label></td>
						<td><label for="pid_${p.name }">${p.annualizedReturn }</label></td>
						<td><label for="pid_${p.name }">${p.interest }</label></td>
						<td><label for="pid_${p.name }"><fmt:formatDate value="${p.startDate }"
								pattern="yyyy-MM-dd" /></label></td>
						<td><label for="pid_${p.name }"><fmt:formatDate value="${p.endDate }"
								pattern="yyyy-MM-dd" /></label></td>
						<td><label for="pid_${p.name }">${p.deadline }</label></td>
						<td><label for="pid_${p.name }"><fmt:formatDate value="${p.returnDate }"
								pattern="yyyy-MM-dd" /></label></td>
						<td><label for="pid_${p.name }">T+${p.valueDay }</label></td>
						<td><label for="pid_${p.name }">${p.moneyNow }</label></td>
						<td><label for="pid_${p.name }">${p.quota }</label></td>
                        <td><label for="pid_${p.name }">${p.badMoney }</label></td>
                        <td><div class="progress" style="height:7px;margin-bottom:7px;">
                            <div class="progress-bar progress-bar-success" role="progressbar" 
                              aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: ${p.numberFormatData}">
                            </div>
                            </div>
                        </td>
					</tr>
				</c:forEach>
			</table>
		</div>
			
		<div class="panel-body">
		<h3 class="panel-title">已结算</h3>
			<table class="table table-hover">
				<tr>
					<td>名称</td>
					<td>金额</td>
					<td>年化收益率</td>
					<td>借款人利息</td>
					<td>开始筹款日期</td>
					<td>结束筹款日期</td>
					<td>筹款期限</td>
					<td>还款日期</td>
					<td>起息方式</td>
					<td>已筹金额</td>
					<td>借款金额</td>
				</tr>
				<c:forEach items="${instance.historyProduct }" var="p">
					<!--此处加判断，判断是否为不活跃，判断条件当前日期不在结束筹款日期（包含）前，并排除钱已筹齐的情况-->
					<tr class="${p.stateName }">
						<td>${p.name }</td>
						<td>${p.quota }</td>
						<td>${p.annualizedReturn }</td>
						<td>${p.interest }</td>
						<td><fmt:formatDate value="${p.startDate }"
								pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${p.endDate }"
								pattern="yyyy-MM-dd" /></td>
						<td>${p.deadline }</td>
						<td><fmt:formatDate value="${p.returnDate }"
								pattern="yyyy-MM-dd" /></td>
						<td>T+${p.valueDay }</td>
						<td>${p.moneyNow }</td>
						<td>${p.quota }</td>
						
					</tr>
				 </c:forEach>
			</table>
		</div>
	</div>
	<div class="panel-body">
	<span >
		<input style="float:right;" type="submit" formaction="/god/jump.do" class="btn btn-info" value="查看结算项目详情"/>
    </span>
    </div>
	</form>
</body>
</html>