<%-- 
    Document   : AlterTable
    Created on : Oct 19, 2014, 5:05:38 PM
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
        <title>Table success</title>
    </head>    
    <body class="home">
        <jsp:useBean id="query_proc" class="Controller.QueryProc" scope="session" ></jsp:useBean>
        
        <div class="header">
                <h1>Growing Database Schemas</h1>
                <ul class="urlParent" >
                    <li><a href="#"> About</a></li>
                    <li><a href="./index.jsp">Start</a></li>
                    <li><a href="./home.html">Home</a></li>
                </ul>
        </div>
        <div class="section">
                          

             <h4>Selected types successfully changed in the schema.</h4>
             <h4>Would you like to enter another query.</h4>
             <form Action="./DatabaseDisplay.jsp" method="Post">
                 
                <!--//TESTING--> 
                <c:forEach items="${query_proc.getRelation(param)}" var="map" >
                     from: ${map.key} to: ${map.value} <br>
                     
                </c:forEach>
                 
                 <input type="Submit" value="Yes" />
             </form>
         </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>
