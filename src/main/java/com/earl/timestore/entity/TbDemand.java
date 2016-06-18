package com.earl.timestore.entity;
// Generated 2016-4-25 12:12:32 by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TbDemand generated by hbm2java
 */
public class TbDemand implements java.io.Serializable {

	private Integer demandid;//����id
	private TbUser tbUserByDemandClient;//������id
	private TbUser tbUserByDemandUser;//������id
	private String demandContent;//��������
	private Integer demandType;//��������
	private BigDecimal demandPay;//Ը�⸶���ļ۸�
	private String demandPhotourljson;//������Ƭ·��
	private Integer demandLikecount;//���޴���
	private String demandTitle;//�������
	private Date demandTime;//���󷢲���Ϣ
	private Date demandAccepttime;//����ʱ��
	private Date demandFinishtime;//���ʱ��
	private Date demandCanceltime;//ȡ��ʱ��
	private Integer demandStatus;//����״̬
	private Integer demandCommentcount;//���۴���
	private Set tbComments = new HashSet(0);
	private Set tbRequests = new HashSet(0);

	public TbDemand() {
	}

	public TbDemand(TbUser tbUserByDemandClient, TbUser tbUserByDemandUser, String demandContent, Integer demandType,
			BigDecimal demandPay, String demandPhotourljson, Integer demandLikecount, String demandTitle,
			Date demandTime, Date demandAccepttime, Date demandFinishtime, Date demandCanceltime, Integer demandStatus,
			Integer demandCommentcount, Set tbComments, Set tbRequests) {
		this.tbUserByDemandClient = tbUserByDemandClient;
		this.tbUserByDemandUser = tbUserByDemandUser;
		this.demandContent = demandContent;
		this.demandType = demandType;
		this.demandPay = demandPay;
		this.demandPhotourljson = demandPhotourljson;
		this.demandLikecount = demandLikecount;
		this.demandTitle = demandTitle;
		this.demandTime = demandTime;
		this.demandAccepttime = demandAccepttime;
		this.demandFinishtime = demandFinishtime;
		this.demandCanceltime = demandCanceltime;
		this.demandStatus = demandStatus;
		this.demandCommentcount = demandCommentcount;
		this.tbComments = tbComments;
		this.tbRequests = tbRequests;
	}

	public Integer getDemandid() {
		return this.demandid;
	}

	public void setDemandid(Integer demandid) {
		this.demandid = demandid;
	}

	public TbUser getTbUserByDemandClient() {
		return this.tbUserByDemandClient;
	}

	public void setTbUserByDemandClient(TbUser tbUserByDemandClient) {
		this.tbUserByDemandClient = tbUserByDemandClient;
	}

	public TbUser getTbUserByDemandUser() {
		return this.tbUserByDemandUser;
	}

	public void setTbUserByDemandUser(TbUser tbUserByDemandUser) {
		this.tbUserByDemandUser = tbUserByDemandUser;
	}

	public String getDemandContent() {
		return this.demandContent;
	}

	public void setDemandContent(String demandContent) {
		this.demandContent = demandContent;
	}

	public Integer getDemandType() {
		return this.demandType;
	}

	public void setDemandType(Integer demandType) {
		this.demandType = demandType;
	}

	public BigDecimal getDemandPay() {
		return this.demandPay;
	}

	public void setDemandPay(BigDecimal demandPay) {
		this.demandPay = demandPay;
	}

	public String getDemandPhotourljson() {
		return this.demandPhotourljson;
	}

	public void setDemandPhotourljson(String demandPhotourljson) {
		this.demandPhotourljson = demandPhotourljson;
	}

	public Integer getDemandLikecount() {
		return this.demandLikecount;
	}

	public void setDemandLikecount(Integer demandLikecount) {
		this.demandLikecount = demandLikecount;
	}

	public String getDemandTitle() {
		return this.demandTitle;
	}

	public void setDemandTitle(String demandTitle) {
		this.demandTitle = demandTitle;
	}

	public Date getDemandTime() {
		return this.demandTime;
	}

	public void setDemandTime(Date demandTime) {
		this.demandTime = demandTime;
	}

	public Date getDemandAccepttime() {
		return this.demandAccepttime;
	}

	public void setDemandAccepttime(Date demandAccepttime) {
		this.demandAccepttime = demandAccepttime;
	}

	public Date getDemandFinishtime() {
		return this.demandFinishtime;
	}

	public void setDemandFinishtime(Date demandFinishtime) {
		this.demandFinishtime = demandFinishtime;
	}

	public Date getDemandCanceltime() {
		return this.demandCanceltime;
	}

	public void setDemandCanceltime(Date demandCanceltime) {
		this.demandCanceltime = demandCanceltime;
	}

	public Integer getDemandStatus() {
		return this.demandStatus;
	}

	public void setDemandStatus(Integer demandStatus) {
		this.demandStatus = demandStatus;
	}

	public Integer getDemandCommentcount() {
		return this.demandCommentcount;
	}

	public void setDemandCommentcount(Integer demandCommentcount) {
		this.demandCommentcount = demandCommentcount;
	}

	public Set getTbComments() {
		return this.tbComments;
	}

	public void setTbComments(Set tbComments) {
		this.tbComments = tbComments;
	}

	public Set getTbRequests() {
		return this.tbRequests;
	}

	public void setTbRequests(Set tbRequests) {
		this.tbRequests = tbRequests;
	}

	@Override
	public String toString() {
		return "TbDemand [demandid=" + demandid + ", tbUserByDemandClient=" + tbUserByDemandClient
				+ ", tbUserByDemandUser=" + tbUserByDemandUser + ", demandContent=" + demandContent + ", demandType="
				+ demandType + ", demandPay=" + demandPay + ", demandPhotourljson=" + demandPhotourljson
				+ ", demandLikecount=" + demandLikecount + ", demandTitle=" + demandTitle + ", demandTime=" + demandTime
				+ ", demandAccepttime=" + demandAccepttime + ", demandFinishtime=" + demandFinishtime
				+ ", demandCanceltime=" + demandCanceltime + ", demandStatus=" + demandStatus + ", demandCommentcount="
				+ demandCommentcount + ", tbComments=" + tbComments + ", tbRequests=" + tbRequests + "]";
	}
	

}