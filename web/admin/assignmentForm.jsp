<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Assignment Form</title>
    </head>
    <body>
        <h2>${assignment != null ? "Edit Assignment" : "Add Assignment"}</h2>

        <form action="${pageContext.request.contextPath}/assignmentList" method="post">
            <input type="hidden" name="action" value="${assignment != null ? 'edit' : 'add'}"/>
            <c:if test="${assignment != null}">
                <input type="hidden" name="id" value="${assignment.id}"/>
            </c:if>

            <label for="title">Title:</label><br>
            <input type="text" id="title" name="title" value="${assignment != null ? assignment.title : ''}" required><br><br>

            <label for="deadline">Deadline:</label><br>
            <input type="date" id="deadline" name="deadline" value="${assignment != null ? assignment.deadline : ''}" required><br><br>

            <label for="courseId">Course:</label><br>
            <select id="courseId" name="courseId" required>
                <c:forEach var="course" items="${courses}">
                    <option value="${course.id}" <c:if test="${assignment != null && assignment.courseId == course.id}">selected</c:if>>
                        ${course.name}
                    </option>
                </c:forEach>
            </select><br><br>

            <button type="submit">${assignment != null ? "Update" : "Add"} Assignment</button>
        </form>

        <a href="${pageContext.request.contextPath}/admin">Back to Admin Panel</a>
    </body>
</html>