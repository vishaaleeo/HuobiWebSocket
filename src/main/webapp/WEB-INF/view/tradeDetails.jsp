<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
</style>
</head>
<body>
<p>
asks &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp bids
</p>
<div>

 <c:forEach items="${tradeDetailsMap}" var="tradeDetails">

    <table style="float: left">
    <tr>
        <th>
             Price
        </th>
        <th>
            Amount
         </th>
     </tr>


     <c:forEach items="${tradeDetails.value}" var="bids">
     <tr>
     <td> <c:out value="${bids.key}"/></td>
     <td> <c:out value="${bids.value}"/></td>
     </tr>
     </c:forEach>

</table>

 </c:forEach>
</div>

</body>
</html>