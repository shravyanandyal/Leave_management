<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manager Dashboard</title>
</head>
<body>
    <h2>Manager Dashboard</h2>

    <p>Welcome, <c:out value="${sessionScope.loggedUser.firstName}" /></p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/manager/pending-requests">Pending Requests</a></li>
        <li><a href="${pageContext.request.contextPath}/manager/processed-requests">Processed Requests</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    </ul>
</body>
</html>
