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
import java.util.Arrays;
import java.util.List;

public class OrganizationServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                request.setAttribute("resume", r);
                request.setAttribute("buttonValue", "update");
                request.getRequestDispatcher("/WEB-INF/jsp/resume.jsp").forward(request, response);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "addOrganization":
            case "editOrganization":
            case "deletePosition":
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                SectionType sectionType = SectionType.valueOf(request.getParameter("sectionType"));
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
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }


}
