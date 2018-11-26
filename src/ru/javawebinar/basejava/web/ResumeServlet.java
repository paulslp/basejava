package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        String action = request.getParameter("action");
        Resume r;
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "update":
                r = storage.get(uuid);
                r.setFullName(fullName);
                for (ContactType type : ContactType.values()) {
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.addContact(type, value);
                    } else {
                        r.getContacts().remove(type);
                    }
                }
                for (SectionType type : SectionType.values()) {
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.addSection(type, type.htmlToSection(value));
                    } else {
                        r.getContacts().remove(type);
                    }
                }
                storage.update(r);
                response.sendRedirect("resume");
                break;
            case "insert":
                r = new Resume(uuid, fullName);
                for (ContactType type : ContactType.values()) {
                    String value = request.getParameter(type.name());
                    r.addContact(type, value);
                }
                for (SectionType type : SectionType.values()) {
                    String value = request.getParameter(type.name());
                    r.addSection(type, type.htmlToSection(value));
                }
                storage.save(r);
                response.sendRedirect("resume");
                break;
            case "insertOrganization":
                r = storage.get(uuid);
                SectionType sectionType = SectionType.valueOf(request.getParameter("sectionType"));

                OrganizationSection section = (OrganizationSection) r.getSection(sectionType);
                Organization organization = new Organization(request.getParameter("name"), request.getParameter("link"),
                        new Organization.Position(LocalDate.parse(request.getParameter("startDate")), LocalDate.parse(request.getParameter("endDate"))
                                , request.getParameter("title"), request.getParameter("description")));
                if (section == null) {
                    r.addSection(sectionType, new OrganizationSection(organization));
                } else {
                    List<Organization> organizationList = section.getOrganizations();
                    organizationList.add(organization);
                }
                storage.update(r);
                request.setAttribute("resume", r);
                request.setAttribute("sectionType", sectionType);
                request.getRequestDispatcher("/WEB-INF/jsp/addOrganization.jsp").forward(request, response);
                break;
            case "updateOrganization":
                r = storage.get(uuid);
                Integer organizationIndex = Integer.valueOf(request.getParameter("organizationIndex"));
                sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                section = (OrganizationSection) r.getSection(sectionType);
                organization = new Organization(request.getParameter("name"), request.getParameter("link"),
                        new Organization.Position(LocalDate.parse(request.getParameter("startDate")), LocalDate.parse(request.getParameter("endDate"))
                                , request.getParameter("title"), request.getParameter("description")));
                if (section == null) {
                    r.addSection(sectionType, new OrganizationSection(organization));
                } else {
                    section.getOrganizations().set(organizationIndex, organization);
                }
                storage.update(r);

                request.setAttribute("resume", r);
                request.setAttribute("sectionType", sectionType);
                request.setAttribute("organizationIndex", organizationIndex);
                request.setAttribute("organization", organization);

                request.getRequestDispatcher("/WEB-INF/jsp/editOrganization.jsp").forward(request, response);
                break;

            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "create":
                r = new Resume("");
                request.setAttribute("resume", r);
                request.getRequestDispatcher("/WEB-INF/jsp/create.jsp").forward(request, response);
                return;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                request.getRequestDispatcher(
                        ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                ).forward(request, response);
                return;
            case "addEXPERIENCE":
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                request.setAttribute("sectionType", SectionType.EXPERIENCE);
                request.getRequestDispatcher("/WEB-INF/jsp/addOrganization.jsp")
                        .forward(request, response);
                return;
            case "addEDUCATION":
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                request.setAttribute("sectionType", SectionType.EDUCATION);
                request.getRequestDispatcher("/WEB-INF/jsp/addOrganization.jsp")
                        .forward(request, response);
                return;
            case "editOrganization":
                r = storage.get(uuid);
                SectionType sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                Integer organizationIndex = Integer.parseInt(request.getParameter("organizationIndex"));
                Organization organization = ((OrganizationSection) r.getSection(sectionType)).getOrganizations().get(organizationIndex);
                request.setAttribute("resume", r);
                request.setAttribute("sectionType", sectionType);
                request.setAttribute("organizationIndex", organizationIndex);
                request.setAttribute("organization", organization);

                request.getRequestDispatcher("/WEB-INF/jsp/editOrganization.jsp")
                        .forward(request, response);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

    }
}