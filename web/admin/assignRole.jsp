<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Assign Role</title>
    </head>
    <body>
    <h2>Promote Student to Admin</h2>

    <c:if test="${not empty message}">
        <p style="color:green;">${message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <c:if test="${empty message and empty error}">
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.name}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/assignRole" method="post" style="display:inline;">
                                <input type="hidden" name="user_id" value="${user.id}" />
                                <button type="submit">Promote as Admin</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:if test="${empty users}">
            <p>No students available for promotion.</p>
        </c:if>
    </c:if>
    <br>
    <a href="${pageContext.request.contextPath}/admin">Back to Admin Panel</a>
    </body>
</html>