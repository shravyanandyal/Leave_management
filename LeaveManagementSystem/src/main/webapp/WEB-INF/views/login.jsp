<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Leave Management System</title>
</head>
<body>
    <h2>Leave Management System — Login</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="email">Email:</label><br/>
        <input id="email" name="email" type="email" required/><br/><br/>

        <label for="password">Password:</label><br/>
        <input id="password" name="password" type="password" required/><br/><br/>

        <button type="submit">Login</button>
    </form>
</body>
</html>
