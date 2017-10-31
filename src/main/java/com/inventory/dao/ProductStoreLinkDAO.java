package com.inventory.dao;


import com.inventory.entity.ProductStoreLink;

import java.util.List;

public interface ProductStoreLinkDAO extends BaseDAO<ProductStoreLink, Void> {
	List<ProductStoreLink> findByProductId(Integer id);
	void updateStock(ProductStoreLink link);

}
