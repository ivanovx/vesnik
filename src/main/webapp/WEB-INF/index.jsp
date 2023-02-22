<%@ page pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Vesnik</title>
    </head>
    <body>
        <form:form action="/search" method="post" modelAttribute="search">
            <form:input path="criteria" />
            <form:button>Search</form:button>
        </form:form>
    </body>
</html>