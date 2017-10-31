package com.inventory.entity;
/**
 * 商品和仓库连接表  主要是库存数据，商品可以分布在多个仓库
 *
 */
public class ProductStoreLink {
	private Store store;
	private Integer stock;
	private Product product;
	private Integer productId;
	private Integer storeId;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
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
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
}
