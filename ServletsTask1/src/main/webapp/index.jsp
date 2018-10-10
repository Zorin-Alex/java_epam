<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html >
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="resources/css/style.css">
</head>

<body>
<div style = "width:500px; margin: auto">
    <div id="dashboard">
        <div id="buttons" style="padding-left: 20px">
            <div class="button">GET</div>
            <div class="button">POST</div>
            <div class="button">PUT</div>
            <div class="button">DELETE</div>
        </div>
        <input type="text" id="newState" style="width: 515px">
    </div>
    <div id="content" style="padding-top: 10px;">

    </div>
    <div id="info" style="text-align: right; padding-top:5px"></div>
</div>
</body>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/script.js"></script>
</html>