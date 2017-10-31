package com.inventory.dao;


import com.inventory.entity.OrderItem;

import java.util.List;

public interface OrderItemDAO extends BaseDAO<OrderItem, Integer> {
	void deleteByOrderNo(String[] nos);
	List<OrderItem> findByOrderNo(String id);
}
