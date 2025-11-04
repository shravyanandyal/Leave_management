<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Dashboard</title>
</head>
<body>
    <h2>Employee Dashboard</h2>

    <p>Welcome,
        <c:out value="${sessionScope.loggedUser.firstName}" default="User"/>
        <c:out value=" ${sessionScope.loggedUser.lastName}" />
    </p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/employee/leaves">View My Leaves</a></li>
        <li><a href="${pageContext.request.contextPath}/employee/leaves/apply">Apply for Leave</a></li>
        <li><a href="${pageContext.request.contextPath}/employee/leaves/balance">Check Leave Balance</a></li>
        <li><a href="${pageContext.request.contextPath}/employee/leaves/policies">View Leave Policies</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
    </ul>

    <hr/>
    <h3>Quick summary</h3>
    <div id="summary">
        <!-- JS will fetch user's leaves summary -->
    </div>

    <script>
        // Fetch /leaves/me and show a quick count (non-blocking)
        fetch('${pageContext.request.contextPath}/leaves/me')
            .then(resp => resp.json())
            .then(data => {
                if (Array.isArray(data)) {
                    const pending = data.filter(r => r.status && r.status.statusName === 'Pending').length;
                    document.getElementById('summary').innerHTML =
                        `<p>Total requests: ${data.length} — Pending: ${pending}</p>`;
                } else {
                    document.getElementById('summary').innerText = 'No leaves found';
                }
            })
            .catch(err => { /* ignore for summary */ });
    </script>
</body>
</html>
