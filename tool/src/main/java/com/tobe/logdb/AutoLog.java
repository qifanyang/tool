package com.tobe.logdb;

import java.util.Date;


public abstract class AutoLog{
	
	@Property(type=FieldType.DATETIME)
	public Date time = new Date();

	public abstract RollingPeriod rollingPeriod();
	
	public long getTime() {
		return time.getTime();
	}
}
