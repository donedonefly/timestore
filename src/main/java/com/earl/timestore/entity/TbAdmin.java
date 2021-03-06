package com.earl.timestore.entity;
// Generated 2016-4-25 12:12:32 by Hibernate Tools 3.5.0.Final

/**
 * TbAdmin generated by hbm2java
 */
public class TbAdmin implements java.io.Serializable {

	private Integer adminid; //管理员id
	private String adminAccount;//管理员账号
	private String adminPassword;//管理员密码
	private Integer adminAuthority;//管理员权限
	private String adminName;//管理员姓名

	public TbAdmin() {
	}

	public TbAdmin(String adminAccount, String adminPassword, Integer adminAuthority, String adminName) {
		this.adminAccount = adminAccount;
		this.adminPassword = adminPassword;
		this.adminAuthority = adminAuthority;
		this.adminName = adminName;
	}

	public Integer getAdminid() {
		return this.adminid;
	}

	public void setAdminid(Integer adminid) {
		this.adminid = adminid;
	}

	public String getAdminAccount() {
		return this.adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public String getAdminPassword() {
		return this.adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public Integer getAdminAuthority() {
		return this.adminAuthority;
	}

	public void setAdminAuthority(Integer adminAuthority) {
		this.adminAuthority = adminAuthority;
	}

	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Override
	public String toString() {
		return "TbAdmin [adminid=" + adminid + ", adminAccount=" + adminAccount + ", adminPassword=" + adminPassword
				+ ", adminAuthority=" + adminAuthority + ", adminName=" + adminName + "]";
	}

	
}
