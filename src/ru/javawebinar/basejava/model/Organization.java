package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.CheckUtil.getStringChecking;
import static ru.javawebinar.basejava.util.DateUtil.NOW;
import static ru.javawebinar.basejava.util.DateUtil.of;
import static ru.javawebinar.basejava.util.CheckUtil.getDateChecking;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;


    private Link homePage;
    private List<Position> positions = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }


    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return "Organization(" + homePage + "," + positions + ')';
    }

    public void removePosition(int positionIndex) {
        positions.remove(positionIndex);
    }

    public String toHtmlPosition(String uuid, SectionType sectionType, int organizationIndex) {
        String htmlText = "<table>";
        int i = 0;
        for (Position position : positions) {
            if (i > 0) {
                htmlText = htmlText + "<tr><td><a href=\"resume?uuid=" + uuid + "&action=deletePosition&sectionType=" + sectionType.name() + "&organizationIndex=" + organizationIndex + "&positionIndex=" + String.valueOf(i) + "\"><img src=\"img/delete.png\"></a></td>"
                        + "<td><a href=\"resume?uuid=" + uuid + "&action=editPosition&sectionType=" + sectionType.name() + "&organizationIndex=" + organizationIndex + "&positionIndex=" + String.valueOf(i) + "\"><img src=\"img/pencil.png\"></a></td>"
                        + "<td>" + getDateChecking(position.startDate) + "</td><td>" + getDateChecking(position.endDate) + "</td><td>" + getStringChecking(position.title) + "</td><td>" + getStringChecking(position.description) + "</td></tr>";
            }
            i++;
        }

        return htmlText + "</table>";
    }

    public String toHtmlView() {
        return "<tr><td valign=\"top\" align=\"right\">" + homePage.toHtmlView() + "</td>"
                + positions.stream().map(position -> position.toHtml()).reduce("", (acc, x) -> acc + x);
    }

    public String toHtmlEdit(String uuid, SectionType sectionType, int organizationIndex) {
        return "<tr><td>" + homePage.toHtmlEdit(uuid, sectionType, organizationIndex) + "</td>"
                + positions.stream().map(position -> position.toHtml()).reduce("", (acc, x) -> acc + x);
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Position() {
        }

        public Position(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            if ((description == null) || (description.equals(""))) {
                this.description = null;
            } else {
                this.description = description;
            }
        }


        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate) &&
                    Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return "Position(" + startDate + ',' + endDate + ',' + title + ',' + description + ')';
        }


        public String toHtml() {
            return "<td>" + getDateChecking(startDate) + "</td><td>" + getDateChecking(endDate) + "</td><td>" + getStringChecking(title) + "</td><td>" + getStringChecking(description) + "</td></tr><tr><td></td><td></td><td></td>";
        }
    }
}