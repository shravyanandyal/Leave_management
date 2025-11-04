<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Leaves</title>
</head>
<body>
    <h2>My Leave Requests</h2>
    <button onclick="location.href='${pageContext.request.contextPath}/employee/dashboard'">Back</button>
    <table border="1" id="leavesTable" style="width:100%; margin-top:10px;">
        <thead>
            <tr>
                <th>Request ID</th>
                <th>Type</th>
                <th>Start</th>
                <th>End</th>
                <th>Total Days</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody></tbody>
    </table>

    <script>
        function loadLeaves() {
            fetch('${pageContext.request.contextPath}/leaves/me')
                .then(r => r.json())
                .then(list => {
                    const tbody = document.querySelector('#leavesTable tbody');
                    tbody.innerHTML = '';
                    if (!Array.isArray(list)) {
                        tbody.innerHTML = '<tr><td colspan="7">No data</td></tr>';
                        return;
                    }
                    list.forEach(l => {
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
                            <td>${l.requestId}</td>
                            <td>${l.leaveType ? l.leaveType.leaveTypeName : '-'}</td>
                            <td>${l.startDate}</td>
                            <td>${l.endDate}</td>
                            <td>${l.totalDays || '-'}</td>
                            <td>${l.status ? l.status.statusName : '-'}</td>
                            <td>
                                ${l.status && l.status.statusName === 'Pending' ?
                                    `<button onclick="cancelLeave(${l.requestId})">Cancel</button>` : ''}
                                <button onclick="viewDocuments(${l.requestId})">Docs</button>
                            </td>`;
                        tbody.appendChild(tr);
                    });
                });
        }

        function cancelLeave(id) {
            fetch('${pageContext.request.contextPath}/leaves/' + id + '/cancel', { method: 'PUT' })
                .then(r => r.json())
                .then(() => loadLeaves())
                .catch(e => alert('Cancel failed'));
        }

        function viewDocuments(requestId) {
            // For simplicity, you could request documents list via separate endpoint if implemented.
            alert('Open document view for request: ' + requestId);
        }

        loadLeaves();
    </script>
</body>
</html>
