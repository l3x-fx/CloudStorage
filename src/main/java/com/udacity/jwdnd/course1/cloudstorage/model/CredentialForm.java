package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private String credId;
    private String url;
    private String username;
    private String password;

    public CredentialForm(String credId, String url, String username, String password) {
        this.credId = credId;
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public CredentialForm() {

    }
    public String getCredId() {
        return credId;
    }

    public void setCredId(String credId) {
        this.credId = credId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
