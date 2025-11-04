<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Apply for Leave</title>
</head>
<body>
    <h2>Apply for Leave</h2>

    <form id="applyForm">
        <label for="leaveTypeId">Leave Type ID (select from admin-configured leave types):</label><br/>
        <input id="leaveTypeId" name="leaveTypeId" type="number" required/><br/><br/>

        <label for="startDate">Start Date (YYYY-MM-DD):</label><br/>
        <input id="startDate" name="startDate" type="date" required/><br/><br/>

        <label for="endDate">End Date (YYYY-MM-DD):</label><br/>
        <input id="endDate" name="endDate" type="date" required/><br/><br/>

        <label for="reason">Reason:</label><br/>
        <textarea id="reason" name="reason" rows="4" cols="40" required></textarea><br/><br/>

        <button type="button" onclick="submitApply()">Submit</button>
        <button type="button" onclick="location.href='${pageContext.request.contextPath}/employee/dashboard'">Cancel</button>
    </form>

    <div id="result" style="color:green;"></div>
    <div id="error" style="color:red;"></div>

    <script>
        function submitApply() {
            const leaveTypeId = document.getElementById('leaveTypeId').value;
            const startDate = document.getElementById('startDate').value;
            const endDate = document.getElementById('endDate').value;
            const reason = document.getElementById('reason').value;

            fetch('${pageContext.request.contextPath}/leaves/apply', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: new URLSearchParams({
                    leaveTypeId, startDate, endDate, reason
                })
            })
            .then(async res => {
                if (!res.ok) {
                    const err = await res.json().catch(() => ({error: 'Unknown error'}));
                    throw err;
                }
                return res.json();
            })
            .then(data => {
                document.getElementById('result').innerText = data.message || 'Applied successfully';
                // optionally redirect to dashboard after success
                setTimeout(() => location.href='${pageContext.request.contextPath}/employee/dashboard', 1200);
            })
            .catch(e => {
                const msg = e && e.error ? e.error : (e && e.message ? e.message : 'Apply failed');
                document.getElementById('error').innerText = msg;
            });
        }
    </script>
</body>
</html>
