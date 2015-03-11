<%-- 
    Document   : TableDisplay
    Created on : Oct 17, 2014, 3:53:18 PM
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
        
        
        ${query_proc.resetDB(param.DBname, param.dbAction)}        
        ${query_proc.setInputVal(param.query)}
        ${query_proc.setDBname(param.DBname)}
        
        
        
        <!--$%{query_proc.setTable_name_process(param.tbl_nm)}-->
        
        
        
        <div class="header">
                <h3>Growing Database Schemas</h3>
                <ul class="urlParent" >
                    <li><a href="#"> About</a></li>
                    <li><a href="./index.jsp">Start</a></li>
                    <li><a href="./home.html">Home</a></li>
                </ul>
        </div>
        <div class="table_section">
            
<!--            <h5>Please chose Type .</h5>-->

            ${query_proc.Input()}

            <h2>Table Name: <c:out value="${query_proc.tableName.get(0)}"/> </h2>

            <form action ="./Relations.jsp" method="post" >
                <table class="table table-striped" style="width:100%" >
                    <tr>
                        <th>Column Name</th>
                        <th>Column Type</th>
                    </tr>
                    <c:forEach items="${query_proc.TBList(query_proc.tableName.get(0), param.DBname)}" var="TB" >
                        <tr>
                            <td> ${TB.FIELD} </td>
                            <td> 
                                <select name="${TB.FIELD}">
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
