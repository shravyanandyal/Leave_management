<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Reports</title>
</head>
<body>
    <h2>All Leave Requests (Admin)</h2>
    <button onclick="location.href='${pageContext.request.contextPath}/admin/dashboard'">Back</button>

    <div>
        <label>User ID (optional):</label>
        <input id="userId" type="number"/>
        <button onclick="loadReports()">Load</button>
    </div>

    <pre id="reportOutput"></pre>

    <script>
        function loadReports() {
            const userId = document.getElementById('userId').value;
            const url = userId ?
                '${pageContext.request.contextPath}/admin/reports/' + encodeURIComponent(userId) :
                '${pageContext.request.contextPath}/admin/reports';
            fetch(url)
                .then(r => r.json())
                .then(data => {
                    document.getElementById('reportOutput').innerText = JSON.stringify(data, null, 2);
                }).catch(() => document.getElementById('reportOutput').innerText = 'Error loading reports');
        }
    </script>
</body>
</html>
