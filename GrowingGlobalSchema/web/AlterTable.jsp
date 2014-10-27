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
        
    </head>
    <body>
        <jsp:useBean id="alter_table" class="Controller.AlterTable" scope="request" ></jsp:useBean>        
        ${alter_table.alterDDL( query_proc.tableName.get(0), query_proc.columnName, param )}  
        
         <h4>Selected types successfully changed in the schema.</h4>
         <h4>Would you like to enter another query.</h4>
         <form Action="./index.html" method="Post">
             <input type="Submit" value="Yes" />
         </form>
    </body>
</html>
