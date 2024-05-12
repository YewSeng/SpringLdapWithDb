package com.yewseng.service;

import java.util.List;
import javax.naming.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import com.yewseng.dto.LdapUser;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;
    
    public void addUser(LdapUser ldapUser) {
        ldapTemplate.bind("uid=" + ldapUser.getUsername(), null, ldapUser.toAttributes());
    }

    public List<LdapUser> getAllUsers() {
        return ldapTemplate.search("", "(objectclass=inetOrgPerson)", 
                (AttributesMapper<LdapUser>) attributes -> {
                    LdapUser ldapUser = new LdapUser();
                    ldapUser.setCn(attributes.get("cn").get().toString());
                    ldapUser.setSn(attributes.get("sn").get().toString());
                    ldapUser.setUsername(attributes.get("uid").get().toString());
                    ldapUser.setPassword(attributes.get("userPassword").get().toString());
                    return ldapUser;
                });    
    }

    public LdapUser getUserByUid(String uid) {
        List<LdapUser> userDetails = 
                ldapTemplate.search("", "(uid=" + uid + ")", 
                (AttributesMapper<LdapUser>) attributes -> {
                    LdapUser ldapUser = new LdapUser();
                    ldapUser.setCn(attributes.get("cn").get().toString());
                    ldapUser.setSn(attributes.get("sn").get().toString());
                    ldapUser.setUsername(attributes.get("uid").get().toString());
                    ldapUser.setPassword(attributes.get("userPassword").get().toString());
                    return ldapUser;
        });    
        if (userDetails.size() > 0) {
            return userDetails.get(0);
        }
        return null;
    }

    public void deleteUser(String uid) {
        Name userDn = LdapNameBuilder.newInstance("")
                            .add("uid", uid)
                            .build();
        ldapTemplate.unbind(userDn);
    }
     
    public LdapUser updateUser(String uid, LdapUser modifiedLdapUser) {
        var currentUser = getUserByUid(uid);
        if(currentUser != null) {
            deleteUser(uid);
            addUser(modifiedLdapUser);
        }
        return currentUser;
    }
}
