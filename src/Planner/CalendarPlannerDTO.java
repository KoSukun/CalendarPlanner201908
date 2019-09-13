package Planner;

import java.sql.Date;
import java.sql.Time;

public class CalendarPlannerDTO {
	int planId;
	Date effectiveDate;
	
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
