<%-- 
    Document   : Interface
    Created on : Oct 10, 2014, 3:23:33 PM
    Author     : Mikayil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <jsp:useBean id="query_proc" class="Controller.QueryProc" scope="session" />

        <c:forEach items="${query_proc.Input(param.query)}" var="item" >
            ${item} <br/>
        </c:forEach>
    </body>
</html>
