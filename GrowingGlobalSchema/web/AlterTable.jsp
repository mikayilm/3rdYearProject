<%-- 
    Document   : AlterTable
    Created on : Oct 19, 2014, 5:05:38 PM
    Author     : Mikayil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="./resources/bootstrap/dist/css/bootstrap.css" rel="stylesheet"/>
        <link href="./resources/css/GDS.css" rel="stylesheet"/>
        <title>Table success</title>
    </head>
    <body class="home">
        <div class="header">
                <h1>Growing Database Schemas</h1>
                <ul class="urlParent" >
                    <li><a href="#"> About</a></li>
                    <li><a href="./index.jsp">Start</a></li>
                    <li><a href="./home.html">Home</a></li>
                </ul>
        </div>
        <div class="section">
            <jsp:useBean id="alter_table" class="Controller.AlterTable" scope="request" ></jsp:useBean>        
            ${alter_table.alterDDL( query_proc.tableName.get(0), query_proc.columnName, param )}  

             <h4>Selected types successfully changed in the schema.</h4>
             <h4>Would you like to enter another query.</h4>
             <form Action="./index.jsp" method="Post">
                 <input type="Submit" value="Yes" />
             </form>
         </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>
