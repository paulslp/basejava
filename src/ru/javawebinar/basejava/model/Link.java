package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

/**
 * gkislin
 * 14.07.2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;

    public Link() {
    }

    public Link(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        if ((url == null) || (url.equals(""))) {
            this.url = null;
        } else {
            this.url = url;
        }
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Link(" + name + ',' + url + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return url != null ? url.equals(link.url) : link.url == null;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    public String toHtmlView() {
    return    "<a href=\""+this.url+"\">"+this.name+"</a>";
    }
    public String toHtmlEdit(String uuid,SectionType sectionType, int organizationIndex) {
        return    "<a href=\"resume?uuid="+uuid+"&action=editOrganization&sectionType="+sectionType.name()+"&organizationIndex="+organizationIndex+"\"><img src=\"img/pencil.png\"></a><a href=\""+this.url+"\">"+this.name+"</a>";
    }
}