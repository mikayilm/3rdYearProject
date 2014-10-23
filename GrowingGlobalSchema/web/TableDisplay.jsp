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
        <title>JSP Table</title>
        <style>
            table, th, td {
                border: 1px solid black;
                border-collapse: collapse;
            }
            th, td {
                padding: 5px;
            }
            th {
                text-align: left;
            }
        </style>
    </head>
    <body>
        <h4>You have not provided the type for the variables in the query.</h4>
        <h4>Please chose one from the Type boxes and then Save.</h4>

        ${query_proc.Input()}

        <h1>Table Name: <c:out value="${query_proc.tableName.get(0)}"/> </h1>

        <form action ="./AlterTable.jsp" method="post" >
            <table style="width:50%">
                <tr>
                    <th>Column NAME</th>
                    <th>Column TYPE</th>
                </tr>
                <c:set var="i" value="0" />
                <c:forEach items="${query_proc.TBList(query_proc.tableName.get(0))}" var="TB" >
                    <tr>
                        <td> ${TB.FIELD} </td>
                        <td> 
                            <select name="${query_proc.columnName[i]}">
                                <c:set var="i" value="${i+1}" />
                                <option value="${TB.TYPE}">${TB.TYPE}</option>
                                <option value="int">INTEGER</option>
                                <option value="datetime">DATETIME</option>
                            </select> 
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br/>
            <input type="submit" value="Save" />
        </form>

    </body>
</html>
