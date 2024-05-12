package com.yewseng.dto;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import lombok.Data;

@Data
public class LdapUser {

	private String cn;
	private String sn;
	private String username; //uid
	private String password; //userPassword
	
	public Attributes toAttributes() {
		Attributes attributes = new BasicAttributes();
		attributes.put("objectClass", "inetOrgPerson");
		attributes.put("cn", cn);
		attributes.put("sn", sn);
		attributes.put("uid", username);
		attributes.put("userPassword", password);
		return attributes;
	}
}
