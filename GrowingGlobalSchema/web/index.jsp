<%-- 
    Document   : index
    Created on : Feb 8, 2015, 9:47:00 PM
    Author     : Mikayil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <div class="header">
            <h3>Growing Database Schemas</h3>
            <ul class="urlParent" >
                <li><a href="#"> About</a></li>
                <li><a href="./index.jsp">Start</a></li>
                <li><a href="./home.html">Home</a></li>
            </ul>
        </div>

        <script>
            function validateForm() {

                // to check if the db name has been entered
                if (document.getElementById("DBname").value === "" ){
                    alert("PLEASE ENTER A DATABASE NAME \nOR CHOSE FROM THE LIST");
                    return false;
                }
                else if(document.getElementById("DBname").value === "chose database name"){
                    alert("PLEASE ENTER A DATABASE NAME \nOR CHOSE FROM THE LIST");
                    return false;
                }
                // to check if the query has been antered
                if (document.getElementById("inpt").value === "") {
                    alert("PLEASE ENTER A QUERY");
                    return false;
                }                                
            }


            $("document").ready(function(){
                $("#slctRE").change(function(){                    
                    $("#selectR").val($(this).val());
                    if ($(this).val() === "Reset")
                        alert("WARNING!\n\
                        \n\nchosing RESET action \nwill delete your previous schema in selected database \n\
                        \nand generate schemas of only currently added queries ");
                });
                
                $("#slct").change(function(){
                    $("#DBname").val($(this).val());
                });  
                
                $("button[name='actBTN']").click(function(){
                    $("#action").val($(this).attr("id"));
                    $("#textForm").submit();
                });
                
            });
            
            
        </script>

        <div class="index_section">

            <form method="POST" action="./upload"  enctype="multipart/form-data" id="formSer" >                
                <h4>Chose a file:</h4><input type="file" name="file" id="file" /> <br>
                <input type="submit" value="Upload" name="upload" id="upload" />
            </form>            

            <form action="./Relations.jsp" method="POST" id="textForm" onsubmit="return validateForm();">                
            <!--<form action="./Controll" method="POST" id="poxfirm" onsubmit="return validateForm();">-->                
                <h4>Enter or chose a database name:</h4><input type="text" name="DBname" id="DBname" /> 
                
                <select id="slct">
                    <c:forEach items="${query_proc.getDBnames()}" var="dbn" >
                        <option value="${dbn}" > ${dbn}</option>
                    </c:forEach>
                </select>

                <h4>Enter or edit queries:</h4><textarea rows="4" cols="52" id="inpt" name="query">${requestScope.test}</textarea>
                <!--<input type="hidden" name="action" value="tabledisplay" />-->
                <input type="hidden" id="action" name="dbAction" value="" />
                <button name="actBTN" id="enlarge">Enlarge or Create schema</button>
                <button name="actBTN" id="reset">Reset and Create schema</button>
                
            </form>

        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>
