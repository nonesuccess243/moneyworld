<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>My JSP 'filesUpload.jsp' starting page</title>
</head>
<body>
<form action="<%=basePath%>investment/FileUploadServlet" enctype="multipart/form-data" method="post">
<input type="file" name="file1"><br>
<input type="file" name="file2"><br>
<input type="file" name="file3"><br>
<input type="submit">
</form>
</body>
</html>