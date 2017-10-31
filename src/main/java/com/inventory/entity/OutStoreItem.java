package com.inventory.entity;

/**
 * 出库单子项
 *
 */
public class OutStoreItem {
    private Integer outstoreitemid;//id

    private Product product;//商品
    private Integer productid;//商品id

    private Store store;//仓库
    private Integer storeid;//。。

    private Integer num;//数量
    
    private String outstoreno;//出库单号
    private OutStore outstore;
    public OutStore getOutstore() {
		return outstore;
	}

	public void setOutstore(OutStore outstore) {
		this.outstore = outstore;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Integer getOutstoreitemid() {
        return outstoreitemid;
    }

    public void setOutstoreitemid(Integer outstoreitemid) {
        this.outstoreitemid = outstoreitemid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOutstoreno() {
        return outstoreno;
    }

    public void setOutstoreno(String outstoreno) {
        this.outstoreno = outstoreno == null ? null : outstoreno.trim();
    }
}