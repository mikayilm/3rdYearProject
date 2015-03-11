<%-- 
    Document   : DatabaseDisplay
    Created on : Feb 28, 2015, 4:26:20 PM
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
        
        <!--$%{query_proc.resetDB(param.DBname, param.action)}-->        
        <!--$%{query_proc.setInputVal(param.query)}-->
        
        <!--$%{query_proc.setDBname(param.DBname)}-->
        <%--<jsp:useBean id="alter_table" class="Controller.AlterTable" scope="request" ></jsp:useBean>        
            ${alter_table.alterDDL(query_proc.DBname, query_proc.tableName.get(0), query_proc.columnName, param )}--%>
        
        <div class="header">
                <h1>Growing Database Schemas</h1>
                <ul class="urlParent" >
                    <li><a href="#"> About</a></li>
                    <li><a href="./index.jsp">Start</a></li>
                    <li><a href="./home.html">Home</a></li>
                </ul>
        </div>
        <div class="database_section">

            <!--$%{query_proc.Input()}-->


            <form action ="./index.jsp" method="post" >
                <c:forEach items="${query_proc.DBnames}" var="db_name" >                    
                    ${db_name}
                    
                    <table class="table table-striped" style="width:100%" >
                            <tr>
                                <th>Table Names</th>
                            </tr>
                        <c:forEach items="${query_proc.getTableNames(db_name)}" var="table" >
                            <tr>
                                <td><a href="./dbToTableDisplay.jsp?DBname=${db_name}&tbl_nm=${table}">${table}</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:forEach>
                <br/>
                <button>Save and Continue</button>
            </form>
        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>