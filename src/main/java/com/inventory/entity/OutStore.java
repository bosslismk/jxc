package com.inventory.entity;

import java.util.Date;
/**
 * 出库单
 *
 */
public class OutStore {
    private String outno;//出库单号

    private String orderno;//订单出库则需要此字段存放订单单号

    private String deliverperson;//发货人

    private String operater;//操作员

    private String reviewer;//审核人

    private Integer type;//出库类型：1订单出库2退货给供货商

    private Integer status;//状态 -1 审核失败 0已取消1待审核 2 审核通过待出库 3出库完成

    private Date createtime;//创建时间

    private Date updatetime;//最后更新时间

    public String getOutno() {
        return outno;
    }

    public void setOutno(String outno) {
        this.outno = outno == null ? null : outno.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getDeliverperson() {
        return deliverperson;
    }

    public void setDeliverperson(String deliverperson) {
        this.deliverperson = deliverperson == null ? null : deliverperson.trim();
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
        this.reviewer = reviewer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status ;
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