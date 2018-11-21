package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        if (action.equals("update")) {
            Resume r = storage.get(uuid);
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
        } else if (action.equals("insert")) {
            Resume r = new Resume(uuid, fullName);
            for (ContactType type : ContactType.values()) {
                String value = request.getParameter(type.name());
                r.addContact(type, value);
            }
            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                r.addSection(type, type.htmlToSection(value));
            }
            storage.save(r);
        } else {
            throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        response.sendRedirect("resume");
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
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

    }
}