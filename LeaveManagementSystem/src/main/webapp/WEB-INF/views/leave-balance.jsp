<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Leave Balance</title>
</head>
<body>
    <h2>Check Leave Balance</h2>
    <button onclick="location.href='${pageContext.request.contextPath}/employee/dashboard'">Back</button>

    <form id="balanceForm">
        <label>Leave Type ID:</label><br/>
        <input id="leaveTypeId" name="leaveTypeId" type="number" required/><br/><br/>
        <label>Financial Year (optional):</label><br/>
        <input id="financialYear" name="financialYear" type="number"/><br/><br/>
        <button type="button" onclick="checkBalance()">Check</button>
    </form>

    <div id="balanceResult"></div>

    <script>
        function checkBalance() {
            const typeId = document.getElementById('leaveTypeId').value;
            const fy = document.getElementById('financialYear').value;
            const url = '${pageContext.request.contextPath}/leaves/balance?leaveTypeId=' + encodeURIComponent(typeId) + (fy ? '&financialYear=' + encodeURIComponent(fy) : '');
            fetch(url)
                .then(r => r.json())
                .then(data => {
                    document.getElementById('balanceResult').innerText = JSON.stringify(data);
                })
                .catch(e => { document.getElementById('balanceResult').innerText = 'Error fetching balance'; });
        }
    </script>
</body>
</html>
