<%-- 
    Document   : TableDisplay
    Created on : Oct 17, 2014, 3:53:18 PM
    Author     : Mikayil
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="./resources/bootstrap/dist/css/bootstrap.css" rel="stylesheet"/>
        <link href="./resources/css/GDS.css" rel="stylesheet"/>
        
        <title>Table</title>
    </head>
    <body class="home">
        <div class="header">
                <h1>Growing Database Schemas</h1>
                <ul class="urlParent" >
                    <li><a href="#"> About</a></li>
                    <li><a href="./index.html">Start</a></li>
                    <li><a href="./home.html">Home</a></li>
                </ul>
        </div>
        <div class="table_section">
            <h5>You have not provided the type for the variables in the query.</h5>
            <h5>Please chose one from the Type boxes and then Save.</h5>

            ${query_proc.Input()}

            <h1>Table Name: <c:out value="${query_proc.tableName.get(0)}"/> </h1>

            <form action ="./AlterTable.jsp" method="post" >
                    <table class="table table-striped" style="width:100%" >
                    <tr>
                        <th>Column NAME</th>
                        <th>Column TYPE</th>
                    </tr>
                    <c:set var="i" value="0" />
                    <c:forEach items="${query_proc.TBList(query_proc.tableName.get(0))}" var="TB" >
                        <tr>
                            <td> ${TB.FIELD} </td>
                            <td> 
                                <!-- columnName[i] is for association between FIELD and TYPE -->
                                <select name="${query_proc.columnName[i]}">
                                    <c:set var="i" value="${i+1}" />
                                    <option value="${TB.TYPE}">${TB.TYPE}</option>
                                    <option value="INT(11)">INTEGER</option>
                                    <option value="datetime">DATETIME</option>
                                </select> 
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <br/>
                <input type="submit" value="Save" />
            </form>
        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>
