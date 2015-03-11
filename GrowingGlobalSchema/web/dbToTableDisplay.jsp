<%-- 
    Document   : page_tableDet
    Created on : Mar 2, 2015, 9:06:12 PM
    Author     : Mikayil
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <jsp:useBean id="query_proc" class="Controller.QueryProc" scope="session" ></jsp:useBean>     
        
        
        ${query_proc.setDBname(param.DBname)}
        
        ${query_proc.setTable_name_process(param.tbl_nm)}

        
        <div class="header">
                <h1>Growing Database Schemas</h1>
                <ul class="urlParent" >
                    <li><a href="#"> About</a></li>
                    <li><a href="./index.jsp">Start</a></li>
                    <li><a href="./home.html">Home</a></li>
                </ul>
        </div>
        <div class="table_section">            

            <!--$%{query_proc.Input()}-->

            <h1>Table Name: <c:out value="${query_proc.table_name_process}"/> </h1>

            <form action ="./DatabaseDisplay.jsp" method="post" >
                <table class="table table-striped" style="width:100%" >
                    <tr>
                        <th>Column NAME</th>
                        <th>Column TYPE</th>
                    </tr>
                    <c:set var="i" value="0" />
                    <c:forEach items="${query_proc.tableList(query_proc.table_name_process, param.DBname)}" var="TB" >
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
                <button>Save and Continue</button>
            </form>
        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>

