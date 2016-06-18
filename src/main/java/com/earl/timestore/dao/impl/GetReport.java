package com.earl.timestore.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.earl.timestore.dao.GetReportDao;

public class GetReport extends SessionFactoryDaoImpl implements GetReportDao {

	public long getChatReport() {
		String hql = "select count(*) from TbChat";
		long chatNum = (Long) getSession().createQuery(hql).uniqueResult();

		return chatNum;
	}

	public long getChatReport(String userName) {
		String hql = "select count(*) from TbChat chat where chat.tbUserByChatFrom.userName=?";
		long chatNum = (Long) getSession().createQuery(hql).setString(0, userName).uniqueResult();

		return chatNum;
	}

	/**
	 * 获得需求报表
	 * 
	 * @return List<Object> [0] 帖子总数 [1] 完成数量 [2] 等待中数量 [3] 流通金额 [4] 流通时间
	 */
	public List<Object> getDemandReport() {
		List<Object> demandReport = new ArrayList<Object>();

		String totalDemandNumHQL = "select count(*) from TbDemand";
		String finishDemandNumHQL = "select count(*) from TbDemand demand where demand.demandStatus=2";
		String waitingDemandNumHQL = "select count(*) from TbDemand demand where demand.demandStatus=0";
		String totalMoneyHQL = "select sum(demand.demandPay) from TbDemand demand where demand.demandType=0";
		String totalTimeHQL = "select sum(demand.demandPay) from TbDemand demand where demand.demandType=1";
		long time = bigDecimalToLong((BigDecimal)getSession().createQuery(totalTimeHQL).uniqueResult());

		demandReport.add(getSession().createQuery(totalDemandNumHQL).uniqueResult());
		demandReport.add(getSession().createQuery(finishDemandNumHQL).uniqueResult());
		demandReport.add(getSession().createQuery(waitingDemandNumHQL).uniqueResult());
		demandReport.add(bigDecimalToLong((BigDecimal)getSession().createQuery(totalMoneyHQL).uniqueResult()));
		demandReport.add(formatDate(time));

		return demandReport;
	}

	/**
	 * 根据日期获得需求报表
	 * 
	 * @param date
	 *            (yyyy-MM-dd)格式
	 * @return List<Object> [0] 帖子总数 [1] 完成数量 [2] 等待中数量 [3] 流通金额 [4] 流通时间
	 */
	public List<Object> getDemandReport(String date) {
		List<Object> demandReport = new ArrayList<Object>();

		String totalDemandNumHQL = "select count(*) from TbDemand demand where demand.demandTime>? and demand.demandTime<?";
		String finishDemandNumHQL = "select count(*) from TbDemand demand where demand.demandStatus=2 and demand.demandTime>? and demand.demandTime<?";
		String waitingDemandNumHQL = "select count(*) from TbDemand demand where demand.demandStatus=0 and demand.demandTime>? and demand.demandTime<?";
		String totalMoneyHQL = "select sum(demand.demandPay) from TbDemand demand where demand.demandType=0 and demand.demandTime>? and demand.demandTime<?";
		String totalTimeHQL = "select sum(demand.demandPay) from TbDemand demand where demand.demandType=1 and demand.demandTime>? and demand.demandTime<?";
		long time = bigDecimalToLong((BigDecimal) getSession().createQuery(totalTimeHQL).setString(0, date)
				.setDate(1, formatDateAfter(date)).uniqueResult());

		demandReport.add(getSession().createQuery(totalDemandNumHQL).setDate(0, formatDateBefore(date))
				.setDate(1, formatDateAfter(date)).uniqueResult());
		demandReport.add(getSession().createQuery(finishDemandNumHQL).setDate(0, formatDateBefore(date))
				.setDate(1, formatDateAfter(date)).uniqueResult());
		demandReport.add(getSession().createQuery(waitingDemandNumHQL).setDate(0, formatDateBefore(date))
				.setDate(1, formatDateAfter(date)).uniqueResult());
		demandReport.add(bigDecimalToLong((BigDecimal)getSession().createQuery(totalMoneyHQL).setDate(0, formatDateBefore(date))
				.setDate(1, formatDateAfter(date)).uniqueResult()));
		demandReport.add(formatDate(time));

		return demandReport;
	}

	/**
	 * 将时间毫秒数格式为 （年月日 时分秒）
	 * 
	 * @param time
	 *            时间毫秒数
	 * @return
	 */
	public String formatDate(long time) {
		long year = time / ((long) 360 * 24 * 60 * 60 * 1000);
		long month = (time - year * 360 * 24 * 60 * 60 * 1000) / ((long) 30 * 24 * 60 * 60 * 1000);
		long day = ((time - year * 360 * 24 * 60 * 60 * 1000) - month * 30 * 24 * 60 * 60 * 1000)
				/ ((long) 24 * 60 * 60 * 1000);
		long hour = (((time - year * 360 * 24 * 60 * 60 * 1000) - month * 30 * 24 * 60 * 60 * 1000)
				- day * 24 * 60 * 60 * 1000) / ((long) 60 * 60 * 1000);
		long minute = ((((time - year * 360 * 24 * 60 * 60 * 1000) - month * 30 * 24 * 60 * 60 * 1000)
				- day * 24 * 60 * 60 * 1000) - hour * 60 * 60 * 1000) / (60 * 1000);
		long second = (((((time - year * 360 * 24 * 60 * 60 * 1000) - month * 30 * 24 * 60 * 60 * 1000)
				- day * 24 * 60 * 60 * 1000) - hour * 60 * 60 * 1000) - minute * 60 * 1000) / 1000;
		String totalTime = year + "年" + month + "月" + day + "天" + hour + "时" + minute + "分" + second + "秒";
		return totalTime;
	}

	/**
	 * 根据date获得时间
	 * 
	 * @param date
	 *            (yyyy-MM-dd)格式字符串
	 * @return
	 * @throws ParseException
	 */
	public Date formatDateBefore(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = null;
		try {
			newDate = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}

	/**
	 * 根据date获得之后一天
	 * 
	 * @param date
	 *            (yyyy-MM-dd)格式字符串
	 * @return
	 * @throws ParseException
	 */
	public Date formatDateAfter(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long time = 0;
		try {
			time = dateFormat.parse(date).getTime() + (long) 24 * 60 * 60 * 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date newDate = new Date(time);
		return newDate;
	}
	
	public long bigDecimalToLong(BigDecimal bigDecimal){
		long longValue = 0;
		if(bigDecimal!=null)
			longValue=bigDecimal.longValue();
		return longValue;
	}

}
