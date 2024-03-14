package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM FILES WHERE credentialid = #{credentialId}")
    Credential getCredential(String credentialId);

    @Insert("INSERT INTO FILES (url, username, xkey, password, userid) VALUES(#{fileName}, #{url}, #{username}, #{xkey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int createCredential(Credential credential);

    @Delete("DELETE FROM FILES where credentialid = #{credentialId}")
    void deleteCredential(Credential credential);
}
