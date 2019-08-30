package com.mcbath.rebecca.firebasedatabasedvirtest1.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * Created by Rebecca McBath
 * on 2019-08-26.
 */

@IgnoreExtraProperties
public class User {

	private String accountId;
	private String userName;
	private String avatarUrl;

	// Needed for Firestore
	public User(){
	}

	public User(String accountId, String userName, String avatarUrl) {
		this.accountId = accountId;
		this.userName = userName;
		this.avatarUrl = avatarUrl;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}
