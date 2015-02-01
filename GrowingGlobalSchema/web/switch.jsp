<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        
        <jsp:useBean id="query_proc" class="Controller.QueryProc" scope="session" >
            <jsp:setProperty name="query_proc" property="inputVal" value="query" />
        </jsp:useBean>
        
        <c:set var="url_" value="${query_proc.getURL(param.query)}" scope="session"></c:set>
        
        <c:redirect url="${url_}"/>        
    </body>
</html>
