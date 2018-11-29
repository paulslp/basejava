<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization" scope="request"/>
    <jsp:useBean id="buttonValue" type="java.lang.String" scope="request"/>
    <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType" scope="request"/>
    <c:if test="${buttonValue.equals(\"updateOrganization\")}">
        <jsp:useBean id="organizationIndex" type="java.lang.Integer" scope="request"/>
        <jsp:useBean id="positions_next" type="java.lang.String" scope="request"/>
    </c:if>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <section>
        <input type="hidden" name="sectionType" value="${sectionType.name()}">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="organizationIndex" value="${organizationIndex}">
        <input type="hidden" name="positions_next" value="${positions_next.replace("\"","''")}">
        <dl>
            <dt>Наименование организации:</dt>
            <dd><input type="text" name="name" size=50 value="${organization.homePage.name}"></dd>
        </dl>
        <dl>
            <dt>Сайт организации</dt>
            <dd><input type="text" name="link" size=50 value="${organization.homePage.url}"></dd>
        </dl>
        <dl>
            <dt>Дата начала:</dt>
            <dd><input type="date" name="startDate" size=50 value="${organization.positions.get(0).startDate}"></dd>
        </dl>
        <dl>
            <dt>Дата конца:</dt>
            <dd><input type="date" name="endDate" size=50 value="${organization.positions.get(0).endDate}"></dd>
        </dl>
        <dl>
            <dt>Должность:</dt>
            <dd><input type="text" name="title" size=50 value="${organization.positions.get(0).title}"></dd>
        </dl>
        <dl>
            <dt>Обязанности:</dt>
            <dd><input type="text" name="description" size=50 value="${organization.positions.get(0).description}"></dd>
        </dl>
        <button type="submit" name="action" value="${buttonValue}">

            <c:choose>
                <c:when test="${buttonValue.equals(\"updateOrganization\")}">
                    Изменить
                </c:when>
                <c:when test="${buttonValue.equals(\"insertOrganization\")}">
                    Добавить
                </c:when>
            </c:choose>

        </button>
        <button onclick="window.history.back()">Отменить</button>
        <a href="resume?uuid=${resume.uuid}&action=edit">Вернуться к редактированию резюме</a>

    </section>
    <section>
        <dl>
            <c:if test="${buttonValue.equals(\"updateOrganization\")}">
                <a href="resume?uuid=${resume.uuid}&action=addPosition&sectionType=${sectionType.name()}&organizationIndex=${organizationIndex}"><img
                        src="img/add.png"></a>
            </c:if>
        </dl>
        <dl>
            ${organization.toHtmlPosition(resume.uuid,sectionType,organizationIndex)}<br/>
            <%--${sectionType.toHtmlView(resume.getSection(sectionType))}<br/>--%>
        </dl>


    </section>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>