<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Student Dashboard</title>
    </head>
    <body>
        <h1>Welcome, ${student.name}</h1>
        <p>Level: ${student.level}</p>
        <a href="/TaskTrack/logout">Logout</a>

        <h2>To‑Do List</h2>
        <table border="1">
          <tr>
              <th>Title</th>
              <th>Course</th>
              <th>Deadline</th>
              <th>Status</th>
              <th>Action</th>
          </tr>
          <c:forEach var="a" items="${assignments}">
            <tr>
                <td>${a.title}</td>
                <td>${a.courseName}</td>
                <td><fmt:formatDate value="${a.deadline}" pattern="dd-MM-yyyy" /></td>
                <td>${a.status}</td>
                <td>
                  <form method="post" action="${pageContext.request.contextPath}/updateAssignmentStatus">
                    <input type="hidden" name="assignmentId" value="${a.id}"/>
                    <select name="status">
                      <option value="Pending" ${a.status=="Pending"?"selected":""}>Pending</option>
                      <option value="Done" ${a.status=="Done"?"selected":""}>Done</option>
                    </select>
                    <button type="submit">Update</button>
                  </form>
                </td>
            </tr>
          </c:forEach>
        </table>
        <c:if test="${empty assignments}">
            <p>No assignments available.</p>
        </c:if>

        <h2>Courses Taken</h2>
        <table border="1">
          <tr>
              <th>Name</th>
              <th>Description</th>
              <th>Status</th>
          </tr>
          <c:forEach var="c" items="${enrolled}">
            <tr>
                <td>${c.name}</td>
                <td>${c.description}</td>
                <td>Enrolled</td>
            </tr>
          </c:forEach>
        </table>
        <c:if test="${empty enrolled}">
            <p>No courses enrolled.</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/enroll">Enroll New Course</a>
    </body>
</html>
