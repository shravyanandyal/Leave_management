<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Processed Leaves</title>
</head>
<body>
    <h2>Processed Leaves (Approved / Rejected)</h2>
    <button onclick="location.href='${pageContext.request.contextPath}/manager/dashboard'">Back</button>

    <div id="list"></div>

    <script>
        fetch('${pageContext.request.contextPath}/manager/processed-requests')
            .then(r => r.json())
            .then(list => {
                document.getElementById('list').innerText = JSON.stringify(list, null, 2);
            })
            .catch(() => document.getElementById('list').innerText = 'Error loading processed requests');
    </script>
</body>
</html>
