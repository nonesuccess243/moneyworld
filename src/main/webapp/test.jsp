<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/component/header.jsp"%>
<title></title>
</head>
<body class="container">

	<div class="page-header">
		<h1>上传图片试试</h1>
	</div>
       <div>
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">添加妇女证件图片</h3>
				</div>
				<div class="panel-body">
					<form method="POST" enctype="multipart/form-data" action="/test.do">
						<div class="form-group">
							<label for="name">名称：</label> 
							<input type="text" name="name" class="form-control" id="name" value=""/>
						</div>

						<div class="form-group">
							<div class="input-group">
							<label for="quota">身份证: </label>
								<input type="file" name="file1" class="form-control"> 
							</div>
						</div>
						
						<div class="form-group">
							<div class="input-group">
							<label for="quota">结婚证: </label>
								<input type="file" name="file2" class="form-control"> 
							</div>
						</div>
						
						<div class="form-group">
							<div class="input-group">
							<label for="quota">驾驶证: </label>
								<input type="file" name="file3" class="form-control"> 
							</div>
						</div>
						
						<div class="form-group">
							<div class="input-group">
							<label for="quota">单身证: </label>
								<input type="file" name="file4" class="form-control"> 
							</div>
						</div>

                        <button type="submit" class="btn btn-primary" value="Upload">添加</button>

					</form>
				</div>
			</div>

		</div>
	</div>
</body>
</html>