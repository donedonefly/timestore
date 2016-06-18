package com.earl.timestore.dao;

import java.util.List;

public interface GetReportDao {

	public abstract long getChatReport();
	public abstract long getChatReport(String userName);
	
	public abstract List<Object> getDemandReport();
	public abstract List<Object> getDemandReport(String date);
	
}
