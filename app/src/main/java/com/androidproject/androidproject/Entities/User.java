package com.androidproject.androidproject.Entities;

import com.androidproject.androidproject.Common.Language;

import java.util.UUID;

public class User {

    private UUID Id;

    /* Check what we need to save in order to manage logins - username \ password \ unique id */

    private Language Lang;

    private Boolean Notifications;

    public User(UUID id, Language language, Boolean notifications) {
        Id = id;
        Lang = language;
        Notifications = notifications;
    }

    public void ChangeLanguage(Language language) {
        Lang = language;
    }

    public void ChangeNotifications(Boolean notifications) {
        Notifications = notifications;
    }

}
