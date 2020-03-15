<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Search results</title>
    </head>
    <body>
        <ol>
            <c:forEach var='result' items='${results}'>
                <li><a href="${result.href}"><c:out value="${result.text}" /></a></li>
            </c:forEach>
        </ol>
    </body>
</html>