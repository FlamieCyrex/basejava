package com.javaops.webapp.model;

public enum ContactType {
    PHONE_NUMBER("Тел:"),
    EMAIL("Почта:"),
    SKYPE("Skype:"),
    TELEGRAM("Telegram:"),
    PROFILE_ON_LINKEDIN("Профиль LinkedIn"),
    PROFILE_ON_GITHUB("Профиль GitHub"),
    PROFILE_ON_STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");
    private String contact;
    private String link;

    ContactType(String contact) {
        this.contact = contact;
    }

    ContactType(String contact, String link) {
        this.contact = contact;
        this.link = link;
    }

    @Override
    public String toString() {
        return contact;
    }
}
