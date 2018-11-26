<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType" scope="request"/>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="sectionType" value="${sectionType}">
        <dl>
            <dt>Наименование организации:</dt>
            <dd><input type="text" name="name" size=50 value=""></dd>
        </dl>
        <dl>
            <dt>Сайт организации</dt>
            <dd><input type="text" name="link" size=50 value=""></dd>
        </dl>
        <dl>
            <dt>Дата начала:</dt>
            <dd><input type="date" name="startDate" size=50 value=""></dd>
        </dl>
        <dl>
            <dt>Дата конца:</dt>
            <dd><input type="date" name="endDate" size=50 value=""></dd>
        </dl>
        <dl>
            <dt>Должность:</dt>
            <dd><input type="text" name="title" size=50 value=""></dd>
        </dl>
        <dl>
            <dt>Обязанности:</dt>
            <dd><input type="text" name="description" size=50 value=""></dd>
        </dl>
        <button type="submit" name="action" value="insertOrganization">Добавить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<section>
        <dl>
            ${sectionType.toHtmlAdd(resume.getSection(sectionType))}<br/>
        </dl>



</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>