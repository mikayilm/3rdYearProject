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
            <h1>Growing Database Schemas</h1>
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
                if (document.getElementById("slctRE").value === "chose action"){
                    alert("PLEASE CHOSE AN ACTION");
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
            });

            $("document").ready(function(){
                $("#slct").change(function(){
                    $("#DBname").val($(this).val());
                });               
            });
            
        </script>

        <div class="index_section">

            <form method="POST" action="./upload"  enctype="multipart/form-data" id="formSer" >                
                <h3>Chose a file:</h3><input type="file" name="file" id="file" /> <br>
                <input type="submit" value="Upload" name="upload" id="upload" />
            </form>            

            <form action="./TableDisplay.jsp" method="POST" id="myForm" onsubmit="return validateForm();">                
                <h3>Enter or chose a database name:</h3><input type="text" name="DBname" id="DBname" /> 
                
                <select id="slct">
                    <c:forEach items="${query_proc.getDBnames()}" var="dbn" >
                        <option value="${dbn}" > ${dbn}</option>
                    </c:forEach>
                </select>

                <h3>Enter or edit queries:</h3><textarea rows="4" cols="50" id="inpt" name="query">${requestScope.test}</textarea>
                <select id="slctRE" name="selectR">
                    <option>chose action</option>
                    <option>Reset</option>
                    <option>Enlarge</option>
                </select>
                <input type="submit" name="enlarge" value="done"  /> 
<!--                <input type="submit" name="reset" value="reset and generate schema" id="reset"/>-->
            </form>

        </div>
        <div class="footer">
            Copyright © 2014. Mikayil Murad.
        </div>
    </body>
</html>
