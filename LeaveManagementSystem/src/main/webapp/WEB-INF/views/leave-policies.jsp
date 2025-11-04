<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Leave Policies</title>
</head>
<body>
    <h2>Leave Policies</h2>
    <button onclick="location.href='${pageContext.request.contextPath}/employee/dashboard'">Back</button>

    <div id="policies"></div>

    <script>
        // Calls /leaves/policies
        fetch('${pageContext.request.contextPath}/leaves/policies')
            .then(r => r.json())
            .then(data => {
                document.getElementById('policies').innerText = JSON.stringify(data, null, 2);
            })
            .catch(() => document.getElementById('policies').innerText = 'Error loading policies');
    </script>
</body>
</html>
