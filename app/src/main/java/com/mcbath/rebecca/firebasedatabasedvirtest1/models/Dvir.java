package com.mcbath.rebecca.firebasedatabasedvirtest1.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

/**
 * Created by Rebecca McBath
 * on 2019-08-26.
 */

@IgnoreExtraProperties
public class Dvir {

	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String mobileId;
	private String mobileName;
	private String createdBy;
	private Inspection inspection;


	public Dvir(){
	}

	public Dvir(Timestamp createdDate, Timestamp modifiedDate, String mobileId, String mobileName, String createdBy, Inspection inspection) {
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.mobileId = mobileId;
		this.mobileName = mobileName;
		this.createdBy = createdBy;
		this.inspection = inspection;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	public String getMobileName() {
		return mobileName;
	}

	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}


}
