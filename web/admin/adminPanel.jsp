<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*, tasktrack.models.*" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Admin Panel</title>
    </head>
    <body>
        <h1>Admin Panel</h1>
        <a href="/TaskTrack/logout">Logout</a>

        <div class="section">
            <h2>Student Progress</h2>
            <table border="1">
                <tr><th>Name</th><th>Level</th><th>Courses Taken</th><th>Assignments Completed</th></tr>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.name}</td>
                        <td>${student.level}</td>
                        <td>
                            <c:choose>
                                <c:when test="${empty student.course_names}">
                                    <em>No course taken</em>
                                </c:when>
                                <c:otherwise>
                                    ${student.course_names}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${student.completed}</td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${empty students}">
                <p>No student data available.</p>
            </c:if>
        </div>

        <div class="section">
            <h2>Course Management</h2>
            <a href="${pageContext.request.contextPath}/courseList">Add Course</a>
            <table border="1">
                <tr><th>Name</th><th>Description</th><th>Actions</th></tr>
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td>${course.name}</td>
                        <td>${course.description}</td>
                        <td>
                            <form method="get" action="${pageContext.request.contextPath}/courseList" style="display:inline">
                                <input type="hidden" name="id" value="${course.id}" />
                                <button type="submit">Edit</button>
                            </form>
                            <form method="post" action="${pageContext.request.contextPath}/deleteCourse">
                                <input type="hidden" name="id" value="${course.id}" />
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${empty courses}">
                <p>No courses available.</p>
            </c:if>
        </div>

        <div class="section">
            <h2>Assignment Management</h2>
            <a href="${pageContext.request.contextPath}/assignmentList">Add Assignment</a>
            <table border="1">
                <tr><th>Title</th><th>Deadline</th><th>Course</th><th>Actions</th></tr>
                <c:forEach var="assignment" items="${assignments}">
                    <tr>
                        <td>${assignment.title}</td>
                        <td><fmt:formatDate value="${assignment.deadline}" pattern="dd-MM-yyyy" /></td>
                        <td>${assignment.course_name}</td>
                        <td>
                            <form method="get" action="${pageContext.request.contextPath}/assignmentList" style="display:inline">
                                <input type="hidden" name="id" value="${assignment.id}" />
                                <button type="submit">Edit</button>
                            </form>
                            <form method="post" action="${pageContext.request.contextPath}/deleteAssignment">
                                <input type="hidden" name="id" value="${assignment.id}" />
                                <button type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${empty assignments}">
                <p>No assignments available.</p>
            </c:if>
        </div>

        <div class="section">
            <h2>Assign Role</h2>
            <a href="${pageContext.request.contextPath}/assignRole">Promote Student to Admin</a>
        </div>
    </body>
</html>
