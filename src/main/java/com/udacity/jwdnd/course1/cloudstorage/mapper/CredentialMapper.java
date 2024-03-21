package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(String userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, xkey, password, userid) VALUES(#{url}, #{userName}, #{xkey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int createCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username= #{userName}, xkey =#{xkey}, password=#{password} ")
        int updateCredential(Credential credential);
    @Delete("DELETE FROM CREDENTIALS where credentialid=#{credentialId}")
        int deleteCredential(String credentialId);
}
