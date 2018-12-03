package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Organization;
import ru.javawebinar.basejava.model.OrganizationSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.JsonParser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class PositionServlet extends HttpServlet {

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

            case "insertPosition":
            case "updatePosition":
                r = storage.get(uuid);
                int organizationIndex = Integer.valueOf(request.getParameter("organizationIndex"));
                SectionType sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                OrganizationSection section = (OrganizationSection) r.getSection(sectionType);
                String organizationJson = request.getParameter("organizationJson").replace("''", "\"");
                Organization organization = JsonParser.read(organizationJson, Organization.class);

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
            case "addPosition":
            case "editPosition":
                uuid = request.getParameter("uuid");
                r = storage.get(uuid);
                SectionType sectionType = SectionType.valueOf(request.getParameter("sectionType"));
                int organizationIndex = Integer.parseInt(request.getParameter("organizationIndex"));
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


}
