package com.androidproject.androidproject.Entities;

import com.androidproject.androidproject.Common.Language;

import java.util.UUID;

public class UserConfiguration {

    // TODO - Instantiate a global Configuration after log in \ when log in approved

    private String Id;

    private String Lang;

    private Boolean Notifications;

    public UserConfiguration(String id, String language, Boolean notifications) {
        Id = id;
        Lang = language;
        Notifications = notifications;
    }

    public void SetLanguage(String language) {
        Lang = language;
    }

    public void SetNotifications(Boolean notifications) {
        Notifications = notifications;
    }

}
