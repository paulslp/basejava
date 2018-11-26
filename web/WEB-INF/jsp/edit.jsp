<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>

    <%--<script>--%>
    <%--window.onload = function() {--%>
    <%--alert( 'Документ и все ресурсы загружены' );--%>
    <%--document.getElementsByName("imgEDUCATION");--%>
    <%--image.hidden=true;--%>
    <%--};--%>
    <%--</script>--%>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}">
                </dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>


        <c:forEach var="sectionType" items="${SectionType.values()}">
            <dl>
                <c:choose>
                    <c:when test="${(sectionType.name().equals(\"PERSONAL\"))||(sectionType.name().equals(\"OBJECTIVE\"))}">
                        <dt>${sectionType.title}</dt>
                        <dd><input type="text" name="${sectionType.name()}" size=30
                                   value="${sectionType.toHtmlEdit(resume.getSection(sectionType))}">
                        </dd>
                    </c:when>

                    <c:when test="${(sectionType.name().equals(\"ACHIEVEMENT\"))||(sectionType.name().equals(\"QUALIFICATIONS\"))}">
                        <dt>${sectionType.title}</dt>
                        <dd>
                            <textarea name="${sectionType.name()}" cols="24"
                                      rows="3">${sectionType.toHtmlEdit(resume.getSection(sectionType))}</textarea>
                        </dd>
                    </c:when>

                    <c:when test="${(sectionType.name().equals(\"EXPERIENCE\"))||(sectionType.name().equals(\"EDUCATION\"))}">
                        ${sectionType.getTitle()} <a href="resume?uuid=${resume.uuid}&action=add${sectionType.name()}"><img
                            src="img/add.png" name="img"${sectionType.name()}></a>
                        ${sectionType.toHtmlEditOrganization(resume.uuid,resume.getSection(sectionType))}<br/>
                    </c:when>
                </c:choose>

            </dl>
        </c:forEach>
        <hr>
        <button type="submit" name="action" value="update">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>