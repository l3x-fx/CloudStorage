package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    final String credentialId;
    final String url;
    final String userName;
    final String xkey;
    final String password;
    final String userId;
    public Credential(String credentialId, String url, String userName, String xkey, String password, String userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.userName = userName;
        this.xkey = xkey;
        this.password = password;
        this.userId = userId;
    }
    public String getCredentialId() {
        return credentialId;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getXkey() {
        return xkey;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }



}
