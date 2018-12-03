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


public class ResumeServlet extends HttpServlet {

    private Storage storage;

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
                    if ((!type.name().equals("EXPERIENCE")) && (!type.name().equals("EDUCATION"))) {
                        String value = request.getParameter(type.name());
                        r.addSection(type, type.htmlToSection(value));
                    }
                }
                storage.save(r);
                response.sendRedirect("resume");
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
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "create":
                r = new Resume("");
                forwardToResume(request, response, r, "insert");
                return;
            case "edit":
                r = storage.get(uuid);
                forwardToResume(request, response, r, "update");
                return;
            case "deleteOrganization":
                r = storage.get(uuid);
                SectionType sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                int organizationIndex = Integer.parseInt(request.getParameter("organizationIndex"));
                ((OrganizationSection) r.getSection(sectionType)).getOrganizations().remove(organizationIndex);
                storage.update(r);
                forwardToResume(request, response, r, "update");
                return;
            case "view":
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

    }

    private void forwardToResume(HttpServletRequest request, HttpServletResponse response, Resume r, String buttonValue) throws ServletException, IOException {
        request.setAttribute("resume", r);
        request.setAttribute("buttonValue", buttonValue);
        request.getRequestDispatcher("/WEB-INF/jsp/resume.jsp").forward(request, response);
    }


}