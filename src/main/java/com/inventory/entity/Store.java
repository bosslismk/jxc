package com.inventory.entity;

import java.util.Date;

/**
 * 仓库
 */
public class Store {
	private Integer storeId;//仓库编号
	private String name;//仓库名称
	private String address;//地址
	private String contactman;//联系人员工编号
	private String tel;//联系人员工电话
	private String email;//电子邮箱
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactman() {
		return contactman;
	}
	public void setContactman(String contactman) {
		this.contactman = contactman;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
