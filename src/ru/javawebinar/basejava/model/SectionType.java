package ru.javawebinar.basejava.model;

import java.util.List;

public enum SectionType {
    PERSONAL("Личные качества") {
    },
    OBJECTIVE("Позиция") {
    },
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtmlView0(Section value) {
            return getTitle() + ": " + String.join(",", ((ListSection) value).getItems());
        }

        @Override
        public String toHtmlEdit0(Section value) {
            return String.join("\n", ((ListSection) value).getItems());
        }

        @Override
        public Section htmlToSection(String value) {
            return new ListSection(value.split(","));
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtmlView0(Section value) {
            return String.join("\n", ((ListSection) value).getItems());
        }

        @Override
        public String toHtmlEdit0(Section value) {
            return String.join("\n", ((ListSection) value).getItems());
        }

        @Override
        public Section htmlToSection(String value) {
            return new ListSection(value.split(","));
        }


    },
    EXPERIENCE("Опыт работы") {
        @Override
        public String toHtmlView0(Section value) {
            return "<table cellspacing=10px>" + "<tr><th>Организация</th><th>Дата начала</th><th>Дата конца</th><th>Должность</th><th>Обязанности</th><th></th></tr>"
                    + ((OrganizationSection) value).getOrganizations().stream().map(organization -> organization.toHtmlView()).reduce("", (acc, x) -> acc + x) + "</table>";
        }

        @Override
        protected String toHtmlAdd0(Section value) {
            return toHtmlView0(value);
        }

        @Override
        public String toHtmlEditOrganization0(String uuid, Section value) {
            String stringHtml = "<table cellspacing=10px>" + "<tr><th>Организация</th><th>Дата начала</th><th>Дата конца</th><th>Должность</th><th>Обязанности</th></tr>";
            List<Organization> organizationList = ((OrganizationSection) value).getOrganizations();
            int i = 0;
            for (Organization organization : organizationList) {
                stringHtml = stringHtml + organization.toHtmlEdit(uuid, SectionType.EXPERIENCE, i);
                i++;
            }
            return stringHtml + "</table>";
        }

        @Override
        public Section htmlToSection(String value) {
            return new ListSection(value.split(","));
        }
    },
    EDUCATION("Образование") {
        @Override
        public String toHtmlView0(Section value) {
            return "<table cellspacing=10px>" + "<tr><th>Организация</th><th>Дата начала</th><th>Дата конца</th><th>Должность</th><th>Обязанности</th></tr>"
                    + ((OrganizationSection) value).getOrganizations().stream().map(organization -> organization.toHtmlView()).reduce("", (acc, x) -> acc + x) + "</table>";
        }

        @Override
        protected String toHtmlAdd0(Section value) {
            return toHtmlView0(value);
        }

        @Override
        public String toHtmlEditOrganization0(String uuid, Section value) {
            String stringHtml = "<table cellspacing=10px>" + "<tr><th>Организация</th><th>Дата начала</th><th>Дата конца</th><th>Должность</th><th>Обязанности</th></tr>";
            List<Organization> organizationList = ((OrganizationSection) value).getOrganizations();
            int i = 0;
            for (Organization organization : organizationList) {
                stringHtml = stringHtml + organization.toHtmlEdit(uuid, SectionType.EDUCATION, i);
                i++;
            }
            return stringHtml + "</table>";
        }

        @Override
        public Section htmlToSection(String value) {
            return new ListSection(value.split(","));
        }

    };

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtmlView0(Section value) {
        return value.toString();
    }

    protected String toHtmlAdd0(Section value) {
        return value.toString();
    }

    protected String toHtmlEditOrganization0(String uuid, Section value) {
        return value.toString();
    }

    protected String toHtmlEdit0(Section value) {
        return value.toString();
    }

    public String toHtmlView(Section value) {
        return (value == null) ? "" : toHtmlView0(value);
    }

    public String toHtmlAdd(Section value) {
        return (value == null) ? "" : toHtmlAdd0(value);
    }

    public String toHtmlEdit(Section value) {
        return (value == null) ? "" : toHtmlEdit0(value);
    }

    public String toHtmlEditOrganization(String uuid, Section value) {
        return (value == null) ? "" : toHtmlEditOrganization0(uuid, value);
    }

    public Section htmlToSection(String value) {
        return new TextSection(value);
    }


}