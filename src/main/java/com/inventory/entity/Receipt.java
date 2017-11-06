package com.inventory.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 收款单
 *
 */
public class Receipt {
    private String receiptno;//收款单号

    private Date receiptmaxdate;//最大收款期限日

    private Integer customerid;//客户id

    private Customer customer;//客户
    
    private BigDecimal amount;//总金额

    private BigDecimal readyamount;//已收金额

    private String operater;//操作员

    private String reviewer;//审核人

    private Integer status;//状态 -1审核失败 0已取消 1待审核2审核通过待收款 3收款完成

    private Integer receipttype;//收款类型1出售订单收款2采购退货收款

    private String remarks;//评论

    private Date createtime;//创建时间

    private Date updatetime;//更新时间

    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno == null ? null : receiptno.trim();
    }

    public Date getReceiptmaxdate() {
        return receiptmaxdate;
    }

    public void setReceiptmaxdate(Date receiptmaxdate) {
        this.receiptmaxdate = receiptmaxdate;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getReadyamount() {
        return readyamount;
    }

    public void setReadyamount(BigDecimal readyamount) {
        this.readyamount = readyamount;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReceipttype() {
        return receipttype;
    }

    public void setReceipttype(Integer receipttype) {
        this.receipttype = receipttype;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}