<%-- 
    Document   : index
    Created on : May 31, 2016, 6:21:28 PM
    Author     : oleksandr
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Admission</title>
	<link rel = "stylesheet" href="index.css">
    </head>
    <body>
        
        <div id="top">
	Student Admission System
	</div>

        <!-- case style format -->
        <c:choose>
            <c:when test="${tab=='Student'}">
                <c:set var="colorST"  value="aqua"/>
                <c:set var="colorCS"  value=""/>
                <c:set var="colorRE"  value=""/>
            </c:when>
            <c:when test="${tab=='Course'}">
                <c:set var="colorST"  value=""/>
                <c:set var="colorCS"  value="aqua"/>
                <c:set var="colorRE"  value=""/>
            </c:when>
            <c:when test="${tab=='Result'}">
                <c:set var="colorST"  value=""/>
                <c:set var="colorCS"  value=""/>
                <c:set var="colorRE"  value="aqua"/>
            </c:when>
            <c:otherwise>
                <c:set var="colorST"  value=""/>
                <c:set var="colorCS"  value=""/>
                <c:set var="colorRE"  value=""/>
            </c:otherwise>
        </c:choose>
	
        <div id="nav">
	<ul>
	<li><a style="background-color:${colorST}" href="<c:url value = 'AppServlet?operation=list&tab=Student'/>">Student</a>
	<li><a style="background-color:${colorCS}" href="<c:url value = 'AppServlet?operation=list&tab=Course'/>">Course</a>
        <li><a style="background-color:${colorRE}" href="<c:url value = 'AppServlet?operation=list&tab=Result'/>">Results</a>
	</div>
        
	<div id="middle">
           
            <c:choose>
                <c:when test="${tab=='Student'}">
                    
                    <form action="AppServlet" method="get">
                        <label>Find student by ID:</label><input type="number" name="id">
                        <input type="hidden" name="operation" value="findId">
                        <input type="hidden" name="tab" value="Student">
                        <input type="submit" value="search!" id="highlight">
                        <a class="midA" href="AppServlet?operation=addForm&tab=Student" id="highlight">Add a student</a>
                    </form>
                    
                <p>${message}</p>
                <table>
                    <tr>
                        <th>id </th>
                        <th>FirstName </th>
                        <th>LastName</th>
                        <th>Gender </th>
                        <th>StartDate </th>
                        <th>Action </th>
                    </tr>
                <c:forEach var="s" items="${list}">
                    <tr style="text-align: left">

                        <td>   <c:out value="${s.id}" />           </td>
                        <td>   <c:out value="${s.firstName}" />    </td>
                        <td>   <c:out value="${s.lastName}" />     </td>
                        <td>   <c:out value="${s.gender}" />     </td>
                        <td>   <c:out value="${s.startDate}" />     </td>         
                        <td>
                            <a href="AppServlet?tab=Student&id=${s.id}&operation=editForm">edit</a> &nbsp&nbsp
                            <a href="AppServlet?tab=Student&id=${s.id}&operation=delete">delete</a>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
                    <br>
                <form action="AppServlet" method="get">
                    <label>Get list by email: provide your address:</label><input type="text" name="mailAddr">
                    <input type="hidden" name="operation" value="email">
                    <input type="hidden" name="tab" value=${tab}>
                    <input type="submit" value="get!" id="highlight">
                </form> 
                </c:when>
                 
                <c:when test="${tab=='Course'}">
                    
                    <form action="AppServlet" method="get">
                        <label>Find course by ID:</label><input type="number" name="id">
                        <input type="hidden" name="operation" value="findId">
                        <input type="hidden" name="tab" value="Course">
                        <input type="submit" value="search!" id="highlight">
                        <a class="midA" href="AppServlet?operation=addForm&tab=Course" id="highlight">Add a course</a>
                    </form>
                    
                <p>${message}</p>
                <table>
                    <tr>
                        <th>Course ID </th>
                        <th>Course Name </th>
                        <th>Action</th>
                    </tr>
                <c:forEach var="cs" items="${list}">
                    <tr style="text-align: left">
                        <td>   <c:out value="${cs.id}" />           </td>
                        <td>   <c:out value="${cs.name}" />    </td>     
                        <td>
                            <a href="AppServlet?tab=Course&id=${cs.id}&operation=editForm">edit</a> &nbsp&nbsp
                            <a href="AppServlet?tab=Course&id=${cs.id}&operation=delete">delete</a>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
                <br>
                <form action="AppServlet" method="get">
                    <label>Get list by email: provide your address:</label><input type="text" name="mailAddr">
                    <input type="hidden" name="operation" value="email">
                    <input type="hidden" name="tab" value=${tab}>
                    <input type="submit" value="get!" id="highlight">
                </form> 
                </c:when>
                
                <c:when test="${tab=='Result'}">
                    
                   <form action="AppServlet" method="get">
                        <label>Find Result
                             by stud Id: &nbsp</label><input type="number" name="sid" style="width:50px;">
                        <label>and cs Id:&nbsp</label><input type="number" name="cid" style="width:50px;">
                        <input type="hidden" name="operation" value="findId">
                        <input type="hidden" name="tab" value="Result">
                        <input type="submit" value="search!" id="highlight">
                        <a class="midA" href="AppServlet?operation=addForm&tab=Result" id="highlight">Add a result</a>
                    </form>                  
                <p>${message}</p>
                <table>
                    <tr>
                        <th>Student ID </th>
                        <th>Course ID </th>
                        <th>Mark1 </th>
                        <th>Mark2 </th>
                        <th>Action</th>
                    </tr>
                <c:forEach var="r" items="${list}">
                    <tr style="text-align: left">
                        <td><a href="AppServlet?tab=Student&id=${r.studentId}&operation=findId">${r.studentId}</a></td>
                        <td><a href="AppServlet?tab=Course&id=${r.courseId}&operation=findId">${r.courseId}</a></td> 
                        <td>   <c:out value="${r.mark1}" />    </td> 
                        <td>   <c:out value="${r.mark2}" />    </td> 
                        <td>
                            <a href="AppServlet?tab=Result&sid=${r.studentId}&cid=${r.courseId}&operation=editForm">edit</a> &nbsp&nbsp
                            <a href="AppServlet?tab=Result&sid=${r.studentId}&cid=${r.courseId}&operation=delete">delete</a>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
                <br>
                <form action="AppServlet" method="get">
                    <label>Get list by email: provide your address:</label><input type="text" name="mailAddr">
                    <input type="hidden" name="operation" value="email">
                    <input type="hidden" name="tab" value=${tab}>
                    <input type="submit" value="get!" id="highlight">
                </form> 
                </c:when>
                
                <c:otherwise>
                    <p>Welcome to Student Admission System!</p>
                    <p>Please choose any option on the right.</p>
                </c:otherwise>
             </c:choose>          
	</div>
           
	<div id="footer">
	(c) Oleksandr / MCIT programming For Mobile Dev class, 2016 
	</div>
    </body>
</html>
