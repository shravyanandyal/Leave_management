<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
    <h2>Admin Dashboard</h2>
    <p>Welcome, <c:out value="${sessionScope.loggedUser.firstName}" /></p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/create-user-page">Create User</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/reports-page">View Reports</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    </ul>
</body>
</html>
