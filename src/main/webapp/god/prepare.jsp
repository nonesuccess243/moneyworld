<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员-开始模拟</title>
<%@ include file="/component/header.jsp" %>

<style type="text/css">
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>

</head>
<body>

<div class="container">

      <form class="form-signin" action="/god/start.do" method="post">
        <h2 class="form-signin-heading">沙盘模拟-开始</h2> 
        <label for="startDate" >开始日期：</label> 
        <input id="startDate" name="startDate" value="2016-01-01" class="form-control" required autofocus>
        <label for="inputPassword">名称：</label>
        <input id="key" name="key" class="form-control" value="1" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">开始模拟</button>
      </form>  

    </div> <!-- /container -->

</body>
</html>