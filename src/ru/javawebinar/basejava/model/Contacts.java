package ru.javawebinar.basejava.model;

public class Contacts {

    public String getTypeContact() {
        return typeContact;
    }

    public void setTypeContact(String typeContact) {
        this.typeContact = typeContact;
    }

    public String getValueContact() {
        return valueContact;
    }

    public void setValueContact(String valueContact) {
        this.valueContact = valueContact;
    }

    public String getAliasValueContact() {
        return aliasValueContact;
    }

    public void setAliasValueContact(String aliasValueContact) {
        this.aliasValueContact = aliasValueContact;
    }

    String typeContact;
    String valueContact;
    String aliasValueContact;


}
