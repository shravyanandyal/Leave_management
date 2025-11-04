<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pending Leaves</title>
</head>
<body>
    <h2>Pending Leaves - My Team</h2>
    <button onclick="location.href='${pageContext.request.contextPath}/manager/dashboard'">Back</button>

    <table border="1" id="pendingTable">
        <thead><tr><th>ID</th><th>Employee</th><th>Type</th><th>Start</th><th>End</th><th>Days</th><th>Action</th></tr></thead>
        <tbody></tbody>
    </table>

    <script>
        function loadPending() {
            fetch('${pageContext.request.contextPath}/manager/pending-requests')
                .then(r => r.json())
                .then(list => {
                    const tbody = document.querySelector('#pendingTable tbody');
                    tbody.innerHTML = '';
                    if (!Array.isArray(list) || list.length === 0) {
                        tbody.innerHTML = '<tr><td colspan="7">No pending requests</td></tr>';
                        return;
                    }
                    list.forEach(item => {
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
                            <td>${item.requestId}</td>
                            <td>${item.user ? (item.user.firstName+' '+(item.user.lastName||'')) : '—'}</td>
                            <td>${item.leaveType ? item.leaveType.leaveTypeName : '-'}</td>
                            <td>${item.startDate}</td>
                            <td>${item.endDate}</td>
                            <td>${item.totalDays || '-'}</td>
                            <td>
                                <button onclick="approve(${item.requestId})">Approve</button>
                                <button onclick="reject(${item.requestId})">Reject</button>
                            </td>`;
                        tbody.appendChild(tr);
                    });
                });
        }

        function approve(id) {
            fetch('${pageContext.request.contextPath}/manager/' + id + '/approve', { method: 'PUT' })
                .then(r => r.json()).then(() => loadPending()).catch(()=> alert('Approve failed'));
        }
        function reject(id) {
            const reason = prompt('Enter rejection reason:');
            if (!reason) return;
            fetch('${pageContext.request.contextPath}/manager/' + id + '/reject?reason=' + encodeURIComponent(reason), { method: 'PUT' })
                .then(r => r.json()).then(() => loadPending()).catch(()=> alert('Reject failed'));
        }

        loadPending();
    </script>
</body>
</html>
