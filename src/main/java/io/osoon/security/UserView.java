package io.osoon.security;

/**
 * @author whiteship
 */
public class UserView {

    private String name;

    public UserView() {
    }

    public UserView(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
