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
        <jsp:useBean id="positions" type="java.lang.String" scope="request"/>
    </c:if>


</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<form method="post" action="organization" enctype="application/x-www-form-urlencoded">
    <section>
        <input type="hidden" name="sectionType" value="${sectionType.name()}">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="organizationIndex" value="${organizationIndex}">
        <input type="hidden" name="positions" value="${positions.replace("\"","''")}">
        <dl>
            <dt>Наименование организации:</dt>
            <dd><input type="text" name="name" size=50 value="${organization.homePage.name}"></dd>
        </dl>
        <dl>
            <dt>Сайт организации</dt>
            <dd><input type="text" name="link" size=50 value="${organization.homePage.url}"></dd>
        </dl>

        <button type="submit" name="action" value="${buttonValue}">
            ${buttonValue.equals("updateOrganization")?"Изменить":"Добавить"}
        </button>
        <button onclick="window.history.back()">Отменить</button>
        <a href="resume?uuid=${resume.uuid}&action=edit">Вернуться к редактированию резюме</a>

    </section>
    <section>
        <dl>
            <c:if test="${buttonValue.equals(\"updateOrganization\")}">
                <a href="position?uuid=${resume.uuid}&action=addPosition&sectionType=${sectionType.name()}&organizationIndex=${organizationIndex}"><img
                        src="img/add.png"></a>
            </c:if>
        </dl>
        <dl>
            <c:if test="${(buttonValue.equals(\"updateOrganization\"))&&(!positions.equals(\"[{}]\"))}">
                ${organization.toHtmlPositions(resume.uuid,sectionType,organizationIndex)}<br/>
            </c:if>
        </dl>
    </section>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>