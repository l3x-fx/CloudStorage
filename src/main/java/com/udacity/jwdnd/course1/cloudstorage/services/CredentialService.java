package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }
    public List<Credential> getCredentials(String userId) {
        return credentialMapper.getCredentials(userId);
    }

    public String decryptPassword(String encryptedPassword, String key){
       return encryptionService.decryptValue(encryptedPassword, key);
    }

    public int addCredential(CredentialForm credentialForm, User user){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);


        return credentialMapper.createCredential(
                new Credential(
                        null,
                        credentialForm.getUrl(),
                        credentialForm.getUsername(),
                        encodedKey,
                        encryptedPassword,
                        user.getUserId()));
    }

    public int updateCredential(CredentialForm credentialForm, User user) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        return credentialMapper.updateCredential(
                new Credential(
                        credentialForm.getCredId(),
                        credentialForm.getUrl(),
                        credentialForm.getUsername(),
                        encodedKey,
                        encryptedPassword,
                        user.getUserId()));
    }

    public int deleteCredential(String credId) {
        return credentialMapper.deleteCredential(credId);
    }
}

