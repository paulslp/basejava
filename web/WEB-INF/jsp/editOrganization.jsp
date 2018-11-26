<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="organizationIndex" type="java.lang.Integer" scope="request"/>
    <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType" scope="request"/>
    <jsp:useBean id="organization" type="ru.javawebinar.basejava.model.Organization" scope="request"/>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="sectionType" value="${sectionType.name()}">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="organizationIndex" value="${organizationIndex}">
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
        <button type="submit" name="action" value="updateOrganization">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
        <a href="resume?uuid=${resume.uuid}&action=edit">Вернуться к редактированию</a>
    </form>
</section>
<section>
    <dl>
        ${sectionType.toHtmlView(resume.getSection(sectionType))}<br/>
    </dl>



</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>