package com.inventory.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 商品
 *
 */
public class Product {
    private Integer productid;

    private String name;

    private Integer categoryid;

    private String model;//型号

    private String unit;//单位

    private BigDecimal inprice;//建议进购价

    private BigDecimal outprice;//建议出售价

    private Integer stocks;//总库存=分仓库总和

    private Integer minstocks;//最小库存

    private Integer maxstocks;//最大库存

    private Date createtime;

    private Date updatetime;
    
    private List<Store> stores;
    
    public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	private ProductCategory category;

    public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getInprice() {
        return inprice;
    }

    public void setInprice(BigDecimal inprice) {
        this.inprice = inprice;
    }

    public BigDecimal getOutprice() {
        return outprice;
    }

    public void setOutprice(BigDecimal outprice) {
        this.outprice = outprice;
    }

    public Integer getStocks() {
        return stocks;
    }

    public void setStocks(Integer stocks) {
        this.stocks = stocks;
    }

    public Integer getMinstocks() {
        return minstocks;
    }

    public void setMinstocks(Integer minstocks) {
        this.minstocks = minstocks;
    }

    public Integer getMaxstocks() {
        return maxstocks;
    }

    public void setMaxstocks(Integer maxstocks) {
        this.maxstocks = maxstocks;
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