<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create User</title>
</head>
<body>
    <h2>Create User</h2>
    <button onclick="location.href='${pageContext.request.contextPath}/admin/dashboard'">Back</button>

    <form id="createForm">
        <label>First Name</label><br/><input id="firstName"/><br/>
        <label>Last Name</label><br/><input id="lastName"/><br/>
        <label>Email</label><br/><input id="email" type="email"/><br/>
        <label>Phone</label><br/><input id="phone"/><br/>
        <label>Role (ADMIN/MANAGER/EMPLOYEE)</label><br/><input id="role"/><br/>
        <label>Password</label><br/><input id="password" type="password"/><br/><br/>
        <button type="button" onclick="createUser()">Create</button>
    </form>

    <div id="msg"></div>

    <script>
        function createUser() {
            const payload = {
                firstName: document.getElementById('firstName').value,
                lastName: document.getElementById('lastName').value,
                email: document.getElementById('email').value,
                phone: document.getElementById('phone').value,
                role: document.getElementById('role').value,
                password: document.getElementById('password').value
            };

            fetch('${pageContext.request.contextPath}/admin/create-user', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(payload)
            }).then(r => {
                if (r.status === 201) return r.json();
                return r.json().then(err => { throw err; });
            }).then(data => {
                document.getElementById('msg').innerText = 'User created: ' + data.userId;
            }).catch(e => {
                const m = e && e.error ? e.error : (e && e.message ? e.message : 'Create failed');
                document.getElementById('msg').innerText = 'Error: ' + m;
            });
        }
    </script>
</body>
</html>
