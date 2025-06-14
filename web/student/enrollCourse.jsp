<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Enroll Course</title>
    </head>
    <body>
        <h2>Enroll Course</h2>
        <table border="1"><tr><th>Name</th><th>Description</th><th>Action</th></tr>
        <c:forEach var="course" items="${courses}">
          <tr><td>${course.name}</td><td>${course.description}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/enroll">
                    <input type="hidden" name="courseId" value="${course.id}"/>
                    <button type="submit">Enroll</button>
                </form>
            </td></tr>
        </c:forEach>
        </table>
        <c:if test="${empty courses}">
            <p>No courses available.</p>
        </c:if>
        <a href="${pageContext.request.contextPath}/student">Back to Dashboard</a>
    </body>
</html>