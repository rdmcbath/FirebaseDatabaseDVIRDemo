package com.mcbath.rebecca.firebasedatabasedvirtest1.models;

import com.google.firebase.database.PropertyName;

/**
 * Created by Rebecca McBath
 * on 2019-08-26.
 */
public class Interior {
	@PropertyName("interior")

	private boolean pass;
	private String comments;
	private String photoUrl;

	public Interior(){

	}

	public Interior(boolean pass, String comments, String photoUrl){
		this.pass = pass;
		this.comments = comments;
		this.photoUrl = photoUrl;
	}

	public boolean getPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
