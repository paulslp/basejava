<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>

    <table cellspacing="15">
        <tr>
            <td align="right"><h2>${resume.fullName}</h2></td>
            <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
        </tr>


        <c:forEach var="contactEntry" items="${resume.contacts}">
            <tr>
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <td valign="middle" align="right"><h3>${contactEntry.key.title}</h3></td>
                <td>${contactEntry.key.toHtml(contactEntry.getValue())}</td>
            </tr>
        </c:forEach>


        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>

            <tr>
                <td valign="middle" align="right"><h3>${sectionEntry.getKey().getTitle()}</h3></td>
                <td>
                    <c:choose>
                        <c:when test="${(sectionEntry.getKey().name().equals(\"PERSONAL\"))||(sectionEntry.getKey().name().equals(\"OBJECTIVE\"))||(sectionEntry.getKey().name().equals(\"EXPERIENCE\"))||(sectionEntry.getKey().name().equals(\"EDUCATION\"))}">
                            ${sectionEntry.key.toHtmlView(sectionEntry.value)}
                        </c:when>

                        <c:when test="${(sectionEntry.getKey().name().equals(\"ACHIEVEMENT\"))||(sectionEntry.getKey().name().equals(\"QUALIFICATIONS\"))}">
                            <pre>${sectionEntry.key.toHtmlView(sectionEntry.value)}</pre>
                        </c:when>

                    </c:choose>

                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>