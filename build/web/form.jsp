<%-- 
    Document   : form
    Created on : May 31, 2016, 6:21:28 PM
    Author     : oleksandr
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert Form</title>
	<link rel = "stylesheet" href="index.css">
        <style>
            form  { text-align:center; display: table;}     
            p     { display: table-row; padding: 10px; }
            label { display: table-cell; text-align: right; padding: 10px;}
            input { display: table-cell; padding: 10px;}
            button{display:table-cell;}
            .fclass {text-align: center; margin: auto; width: 100px; padding:20px;}
        </style>
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
            
            <span>${message}</span><br><br>
            
            <c:choose>
                <c:when test="${tab=='Student'}">
                    <form action="AppServlet" method="get">
                        <p><label>Student ID (1..99):</label><input type="number" name="id" value="${idValue}"></p>
                        <p><label>First Name:</label><input type="text" name="fn" value="${list[0].firstName}"></p>
                        <p><label>Last Name:</label><input type="text" name="ln" value="${list[0].lastName}" ></p>
                        <p><label>Gender (M or F)</label><input type="text" name="gender" value="${list[0].gender}"></p>
                        <p><label>Start Date (yyyy-mm-dd)</label><input type="text" name="sdate" value="${list[0].startDate}"></p>
                        <input type="hidden" name="operation" value="${operationValue}">
                        <input type="hidden" name="tab" value="Student">
                        <p>&nbsp</p>
                        <p><label></label><input class="fclass" type="submit" value="${submitLabel}"></p>
                    </form>
                    
                </c:when>
                 
                <c:when test="${tab=='Course'}">
                    
                   <form action="AppServlet" method="get">
                       <p><label>Course ID (1..99):</label><input type="number" name="id" value="${idValue}"></p>
                       <p><label>Name:</label><input type="text" name="cn" value="${list[0].name}"></p>
                       <p><input type="hidden" name="operation" value="${operationValue}"></p>
                       <p><input type="hidden" name="tab" value="Course"></p>
                       <p>&nbsp</p>
                       <p><label></label><input class="fclass" type="submit" value="${submitLabel}"></p>
                    </form> 
                    
                </c:when>
                
                <c:when test="${tab=='Result'}">
                    
                   <form action="AppServlet" method="get">
                        <p><label>Student ID:</label><input type="number" name="sid" value="${idValue}"></p>
                        <p><label>Course ID:</label><input type="number" name="cid" value="${list[0].courseId}"></p>
                        <p><label>Mark1:(1..99)</label><input type="number" name="m1" value="${list[0].mark1}"></p>
                        <p><label>Mark2:(1..99)</label><input type="number" name="m2" value="${list[0].mark2}"></p>
                        <p><input type="hidden" name="operation" value="${operationValue}"></p>
                        <p><input type="hidden" name="tab" value="Result"></p>
                        <p>&nbsp</p>
                        <p><label></label><input class="fclass" type="submit" value="${submitLabel}"></p>
                    </form>
                    
                </c:when>
                
                <c:otherwise>
                    <p> Welcome! </p>
                </c:otherwise>
             </c:choose>
           
	</div>

        
            
	<div id="footer">
	(c) Oleksandr / MCIT programming For Mobile Dev class, 2016 
	</div>
    </body>
</html>
