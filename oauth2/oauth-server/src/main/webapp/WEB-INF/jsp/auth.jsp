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
<form action="${pageContext.request.contextPath}/auth" method="post">
    <input type="hidden" name="client_id" value="${client_id}">
    <input type="hidden" name="redirect_uri" value="${redirect_uri}">
    <label for="username">
        请输入您的用户名：
        <input type="text" name="username" id="username">
    </label>
    <label for="password">
        请输入您的密码：
        <input type="text" name="password" id="password">
    </label>
    <button>授权</button>
</form>
</body>
</html>
