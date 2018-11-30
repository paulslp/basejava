package ru.javawebinar.basejava.web;


import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.JsonParser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


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
            case "insertOrganization":
            case "updateOrganization":
                r = storage.get(uuid);
                SectionType sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                OrganizationSection section = (OrganizationSection) r.getSection(sectionType);
                Organization organization;
                if (action.equals("insertOrganization")) {
                    organization = new Organization(request.getParameter("name"), request.getParameter("link"));
                    if (section == null) {
                        r.addSection(sectionType, new OrganizationSection(organization));
                    } else {
                        List<Organization> organizationList = section.getOrganizations();
                        organizationList.add(organization);
                    }
                } else {
                    Integer organizationIndex = Integer.valueOf(request.getParameter("organizationIndex"));
                    String positions = request.getParameter("positions").replace("''", "\"");
                    List<Organization.Position> positionList = Arrays.asList(JsonParser.read(positions, Organization.Position[].class));
                    organization = new Organization(new Link(request.getParameter("name"), request.getParameter("link")), positionList);
                    section.getOrganizations().set(organizationIndex, organization);
                }
                storage.update(r);
                forwardToResume(request, response, r, "update");
                break;
            case "insertPosition":
            case "updatePosition":
                r = storage.get(uuid);
                int organizationIndex = Integer.valueOf(request.getParameter("organizationIndex"));
                sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                section = (OrganizationSection) r.getSection(sectionType);
                String organizationJson = request.getParameter("organizationJson").replace("''", "\"");
                organization = JsonParser.read(organizationJson, Organization.class);

                if (action.equals("insertPosition")) {
                    organization.getPositions().add(new Organization.Position(LocalDate.parse(request.getParameter("startDate"))
                            , LocalDate.parse(request.getParameter("endDate"))
                            , request.getParameter("title")
                            , request.getParameter("description")
                    ));
                } else {
                    int positionIndex = Integer.valueOf(request.getParameter("positionIndex"));
                    organization.getPositions().set(positionIndex, new Organization.Position(LocalDate.parse(request.getParameter("startDate"))
                            , LocalDate.parse(request.getParameter("endDate"))
                            , request.getParameter("title")
                            , request.getParameter("description")
                    ));
                }
                ;

                if (section == null) {
                    r.addSection(sectionType, new OrganizationSection(organization));
                } else {
                    section.getOrganizations().set(organizationIndex, organization);
                }
                storage.update(r);
                organization = ((OrganizationSection) r.getSection(sectionType)).getOrganizations().get(organizationIndex);
                String positions = JsonParser.write(organization.getPositions());

                request.setAttribute("resume", r);
                request.setAttribute("sectionType", sectionType);
                request.setAttribute("organizationIndex", organizationIndex);
                request.setAttribute("organization", organization);
                request.setAttribute("positions", positions);
                request.setAttribute("buttonValue", "updateOrganization");
                request.getRequestDispatcher("/WEB-INF/jsp/organization.jsp").forward(request, response);
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
            case "addOrganization":
            case "editOrganization":
            case "deletePosition":
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                request.setAttribute("sectionType", sectionType);
                if (action.equals("addOrganization")) {
                    request.setAttribute("buttonValue", "insertOrganization");
                    request.setAttribute("organization", new Organization("", ""));
                } else {
                    request.setAttribute("buttonValue", "updateOrganization");
                    int orgIndex = Integer.parseInt(request.getParameter("organizationIndex"));

                    if (action.equals("deletePosition")) {
                        int positionIndex = Integer.parseInt(request.getParameter("positionIndex"));
                        ((OrganizationSection) r.getSection(sectionType)).getOrganizations().get(orgIndex).getPositions().remove(positionIndex);
                        storage.update(r);
                    }
                    Organization organization = ((OrganizationSection) r.getSection(sectionType)).getOrganizations().get(orgIndex);
                    String positions = JsonParser.write(organization.getPositions());
                    request.setAttribute("organizationIndex", orgIndex);
                    request.setAttribute("organization", organization);
                    request.setAttribute("positions", positions);
                }
                request.getRequestDispatcher("/WEB-INF/jsp/organization.jsp").forward(request, response);
                return;
            case "addPosition":
            case "editPosition":
                uuid = request.getParameter("uuid");
                r = storage.get(uuid);
                sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                organizationIndex = Integer.parseInt(request.getParameter("organizationIndex"));
                Organization organization = ((OrganizationSection) r.getSection(sectionType)).getOrganizations().get(organizationIndex);
                String organizationJson = JsonParser.write(organization);
                String buttonValue = action.equals("addPosition") ? "insertPosition" : "updatePosition";
                request.setAttribute("resume", r);
                request.setAttribute("sectionType", sectionType);
                request.setAttribute("organizationIndex", organizationIndex);
                request.setAttribute("organizationJson", organizationJson);
                request.setAttribute("buttonValue", buttonValue);

                Organization.Position position;
                if (buttonValue.equals("insertPosition")) {
                    position = new Organization.Position(LocalDate.now(), LocalDate.now(), "", "");
                } else {
                    int positionIndex = Integer.parseInt(request.getParameter("positionIndex"));
                    request.setAttribute("positionIndex", positionIndex);
                    position = organization.getPositions().get(positionIndex);
                }

                request.setAttribute("position", position);
                request.getRequestDispatcher("/WEB-INF/jsp/position.jsp").forward(request, response);
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