package com.example.softwareconcept;

public class RoomsTableController {

    public String getActive_users() {
        return active_users;
    }

    public void setActive_users(String active_users) {
        this.active_users = active_users;
    }

    String active_users;
    public RoomsTableController(String activeUsers) {
        this.active_users = activeUsers;
    }
}
