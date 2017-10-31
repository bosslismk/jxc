package com.inventory.dao;


import com.inventory.entity.OutStoreItem;

import java.util.List;


public interface OutStoreItemDAO extends BaseDAO<OutStoreItem, String> {
	void deleteByOutStoreNo(String[] ids);
	List<OutStoreItem> findByOutStoreNo(String outStoreNo);
}	
