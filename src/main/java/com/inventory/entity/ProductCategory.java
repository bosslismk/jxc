package com.inventory.entity;

import java.util.Date;
/**
 * 商品分类
 *
 */
public class ProductCategory {
	private Integer categoryId;//商品分类id
	private String name;//商品分类名称
	private Date createTime;//创建时间
	private Date updateTime;//最后更新时间
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
