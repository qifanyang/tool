package com.tobe.logdb;


public abstract class AutoLog{
	
	public long time = System.currentTimeMillis();

	public abstract RollingPeriod rollingPeriod();
	
	public long getTime() {
		return time;
	}
}
