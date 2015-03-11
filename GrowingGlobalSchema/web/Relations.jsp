<%-- 
    Document   : Relations
    Created on : Mar 9, 2015, 7:57:34 PM
    Author     : Mikayil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>GDS</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="./resources/css/GDS.css" rel="stylesheet"/>
        <link href="./resources/bootstrap/dist/css/bootstrap.css" rel="stylesheet"/>        
        <script src="./resources/jquery.js" ></script> 
    </head>
    <body class="home">
        <jsp:useBean id="query_proc" class="Controller.QueryProc" scope="session" ></jsp:useBean>  

        <%--<jsp:useBean id="alter_table" class="Controller.AlterTable" scope="request" ></jsp:useBean>--%>        
            ${query_proc.alterDDL(query_proc.DBname, query_proc.tableName.get(0), param )}
        
        <div class="header">
            <h3>Growing Database Schemas</h3>
            <ul class="urlParent" >
                <li><a href="#"> About</a></li>
                <li><a href="./index.jsp">Start</a></li>
                <li><a href="./home.html">Home</a></li>
            </ul>
        </div>

        <div class="table_section">
           <form action="./TableMap.jsp" method="POST">
               <h4>Set a relation between tables</h4>
               <table class="table table-striped" style="width:100%" >
                    <tr>
                        <th>FROM</th>
                        <th>TO</th>
                    </tr>
                    <c:forEach items="${query_proc.getTableNames(query_proc.DBname)}" var="table" >
                    <tr>
                        <td>${table}</td>
                        <td>
                            <select name="${table}">
                                <c:forEach items="${query_proc.getTableNames(query_proc.DBname)}" var="tableSelect" >
                                  <c:if test="${table ne tableSelect}">
                                    <option value="${tableSelect}" > ${tableSelect}</option>
                                  </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    </c:forEach>
                </table>    
                <button name="save" id="save">Save and Continue</button>
                <button name="edit" id="edit">Edit Columns</button>
                <br>
            </form>
        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>
