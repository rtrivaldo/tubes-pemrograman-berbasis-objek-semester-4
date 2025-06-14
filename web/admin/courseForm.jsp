<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Form</title>
    </head>
    <body>
        <h2>${course != null ? "Edit Course" : "Add Course"}</h2>

        <form action="${pageContext.request.contextPath}/${course != null ? 'editCourse' : 'addCourse'}" method="post">
            <input type="hidden" name="action" value="${course != null ? 'edit' : 'add'}"/>
            <c:if test="${course != null}">
                <input type="hidden" name="id" value="${course.id}"/>
            </c:if>

            <label for="name">Course Name:</label><br>
            <input type="text" id="name" name="name" value="${course != null ? course.name : ''}" required><br><br>

            <label for="description">Description:</label><br>
            <textarea id="description" name="description" rows="4" cols="50" required>${course != null ? course.description : ''}</textarea><br><br>

            <button type="submit">${course != null ? "Update" : "Add"} Course</button>
        </form>

        <a href="${pageContext.request.contextPath}/admin">Back to Admin Panel</a>
    </body>
</html>
