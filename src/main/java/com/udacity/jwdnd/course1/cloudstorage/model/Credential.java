package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credential {
    final String credentialId;
    final String url;
    final String userName;
    final String xkey;
    final String password;
    final String userId;

}
