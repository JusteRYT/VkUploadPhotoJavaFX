package com.example.vkupload;

public class VKAccessToken {
    private String accessToken;
    private int userId;

    public VKAccessToken(String accessToken, int userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "VKAccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", userId=" + userId +
                '}';
    }
}
