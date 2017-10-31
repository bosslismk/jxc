package com.inventory.dao;


import com.inventory.entity.ReceiptReady;

import java.util.List;

public interface ReceiptReadyDAO extends BaseDAO<ReceiptReady, Integer> {
	void deleteByReceiptNo(String[] receiptNos);
	List<ReceiptReady> findByReceiptNo(String receiptNo);
}
