package com.inventory.entity;

import java.math.BigDecimal;
/**
 * 收款单已收款项
 */
public class ReceiptReady {
    private Integer receiptreadyno;//id

    private BigDecimal amount;//本次收款金额

    private String receiptperson;//收款人

    private Integer type;//收款方式：1现金支付 2网银 3支付宝 4微信

    private String operater;//操作员

    private String remarks;//评论

    private String receiptno;//收款单号
    
    private Receipt receipt;

    public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Integer getReceiptreadyno() {
        return receiptreadyno;
    }

    public void setReceiptreadyno(Integer receiptreadyno) {
        this.receiptreadyno = receiptreadyno;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReceiptperson() {
        return receiptperson;
    }

    public void setReceiptperson(String receiptperson) {
        this.receiptperson = receiptperson == null ? null : receiptperson.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater == null ? null : operater.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getReceiptno() {
        return receiptno;
    }

    public void setReceiptno(String receiptno) {
        this.receiptno = receiptno == null ? null : receiptno.trim();
    }
}