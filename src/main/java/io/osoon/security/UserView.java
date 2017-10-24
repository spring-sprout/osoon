package io.osoon.security;

/**
 * @author whiteship
 */
public class UserView {

    private String name;

    private String imageUrl;

    public UserView(OSoonUserDetails userDetails) {
        this.name = userDetails.getUser().getName();
        this.imageUrl = userDetails.getUser().getImageUrl();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
