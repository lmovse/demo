<%--
  Created by IntelliJ IDEA.
  User: Jooff
  Date: 2017/8/25
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户授权</title>
</head>
<body>
<a href="${auth_server_uri}?response_type=code&client_id=${client_id}&redirect_uri=${redirect_uri}">前往授权</a>
</body>
</html>
