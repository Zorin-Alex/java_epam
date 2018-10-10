<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<states>
    <c:set var="count" value="0" />
    <c:forEach items="${states}" var="state" >
        <state>
           <id>${count}</id>
           <name>${state}</name>
        </state>
    <c:set var="count" value="${count + 1}"/>
    </c:forEach>
    <count>${sessionScope.countViews}</count>
    <lastView><fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${lastView}" /></lastView>
</states>