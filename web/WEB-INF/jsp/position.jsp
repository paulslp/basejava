<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <%--<jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization" scope="request"/>--%>
    <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position" scope="request"/>
    <jsp:useBean id="buttonValue" type="java.lang.String" scope="request"/>
    <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType" scope="request"/>
    <jsp:useBean id="organizationJson" type="java.lang.String" scope="request"/>
    <jsp:useBean id="organizationIndex" type="java.lang.Integer" scope="request"/>
    <c:if test="${buttonValue.equals(\"updatePosition\")}">
        <jsp:useBean id="positionIndex" type="java.lang.Integer" scope="request"/>
    </c:if>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="sectionType" value="${sectionType.name()}">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <input type="hidden" name="organizationIndex" value="${organizationIndex}">
    <input type="hidden" name="organizationJson" value="${organizationJson.replace("\"","''")}">
    <c:if test="${buttonValue.equals(\"updatePosition\")}">
        <input type="hidden" name="positionIndex" value="${positionIndex}">
    </c:if>
    <dl>
        <dt>Дата начала:</dt>
        <dd><input type="date" name="startDate" size=50 value="${position.startDate}"></dd>
    </dl>
    <dl>
        <dt>Дата конца:</dt>
        <dd><input type="date" name="endDate" size=50 value="${position.endDate}"></dd>
    </dl>
    <dl>
        <dt>Должность:</dt>
        <dd><input type="text" name="title" size=50 value="${position.title}"></dd>
    </dl>
    <dl>
        <dt>Обязанности:</dt>
        <dd><input type="text" name="description" size=50 value="${position.description}"></dd>
    </dl>
    <button type="submit" name="action" value="${buttonValue}">
        <c:choose>
            <c:when test="${buttonValue.equals(\"updatePosition\")}">
                Изменить
            </c:when>
            <c:when test="${buttonValue.equals(\"insertPosition\")}">
                Добавить
            </c:when>
        </c:choose>
    </button>

    <button onclick="window.history.back()">Отменить</button>
    <a href="resume?uuid=${resume.uuid}&action=editOrganization&sectionType=${sectionType}&organizationIndex=${organizationIndex}">Вернуться
        к редактированию организации</a>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>