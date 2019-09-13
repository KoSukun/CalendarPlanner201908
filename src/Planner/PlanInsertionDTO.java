package Planner;

import java.sql.Date;
import java.sql.Time;

public class PlanInsertionDTO {
	int planId;
	String userId;
	Date effectiveDate;
	Time timeCreated;
	String place;
	String note;
	
	public int getPlanId() {
		return planId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Time getTimeCreated() {
		return timeCreated;
	}
	public void setTimeCreated(Time timeCreated) {
		this.timeCreated = timeCreated;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	
}
