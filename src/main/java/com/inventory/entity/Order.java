package com.inventory.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 订单实体类
 *
 */
public class Order {
    private String orderno;//订单编号 yyyyMMddHHmmss{random6}

    private Integer customerid;//客户id
    
    private Customer customer;

    private String saleperson;//销售人员工编号

    private Date saledate;//销售日期

    private Integer paytype;//支付类型 1现金支付 2网银 3支付宝 4微信

    private String address;//地址

    private String remarks;//备注

    private Integer status;//状态  -1审核失败 0已取消 1待审核2审核通过待出库 3出库完成

    private String operater;//操作员编号

    private String reviewer;//审核员编号

    private Date reviewdate;//审核日期
    
    private BigDecimal amount;
    
    public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public String getSaleperson() {
        return saleperson;
    }

    public void setSaleperson(String saleperson) {
        this.saleperson = saleperson == null ? null : saleperson.trim();
    }

    public Date getSaledate() {
        return saledate;
    }

    public void setSaledate(Date saledate) {
        this.saledate = saledate;
    }

    public Integer getPaytype() {
        return paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater == null ? null : operater.trim();
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer == null ? null : reviewer.trim();
    }

    public Date getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(Date reviewdate) {
        this.reviewdate = reviewdate;
    }
}