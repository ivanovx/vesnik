<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Vesnik</title>
</head>
<body>
    <h1>Документи съдържащи думата ${criteria}</h1>
    <ul>
        <c:forEach var="result" items="${results}">
            <li>
                <h3>
                    <c:out value="${result.get('id')}" />
                </h3>
                <div>
                    <c:out value="${result.get('content')}" />
                </div>
            </li>
        </c:forEach>
    </ul>
</body>
</html>