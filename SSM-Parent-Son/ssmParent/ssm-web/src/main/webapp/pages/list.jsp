<%--
  Created by IntelliJ IDEA.
  User: kriss
  Date: 2020/5/20
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <h3>跳转成功......</h3>
<c:forEach var="list" items="${list}">
    ${list.id} ${list.name}       ${list.money}   <br>
</c:forEach>
</body>
</html>
