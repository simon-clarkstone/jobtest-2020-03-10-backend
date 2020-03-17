<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Search results (back end, not using Angular)</title>
    </head>
    <body>
        <a href=".">Back</a>
        <ol>
            <c:forEach var='result' items='${results}'>
                <li>
                    <a href="${result.href}"><c:out value="${result.text}" /></a>
                    (<c:out value="${result.engine}" />)
                </li>
            </c:forEach>
        </ol>
    </body>
</html>