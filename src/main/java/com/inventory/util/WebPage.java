package com.inventory.util;

import java.util.List;

public class WebPage {
	private int size = 10;
	private int start = 0;
	private int totalCount = 0;
	private int currentPage = 1;
	private int totalPage = 0;
	private List<?> result;
	private boolean success;
	private String message;
	private String params;

	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public WebPage(int currentPage, int totalCount) {
		if (currentPage < 1) {
			currentPage = 1;
		}
		this.start = this.size * (currentPage - 1);
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.totalPage = this.totalCount % this.size == 0 ? this.totalCount
				/ this.size : this.totalCount / this.size + 1;
	}
	public WebPage(int currentPage, int totalCount,int size) {
		this.size=size;
		if (currentPage < 1) {
			currentPage = 1;
		}
		this.start = this.size * (currentPage - 1);
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.totalPage = this.totalCount % this.size == 0 ? this.totalCount
				/ this.size : this.totalCount / this.size + 1;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStart() {
		return start;
	}

	public void setTotalCount(int totalCount) {
		this.totalPage = this.totalCount % this.size == 0 ? this.totalCount
				/ this.size : this.totalCount / this.size + 1;
		this.totalCount = totalCount;
		if (totalPage < currentPage) {
			currentPage = totalPage;
			start = size * (currentPage - 1);
		}
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<?> getResult() {
		return result;
	}
	public void setResult(List<?> result) {
		this.result = result;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setStart(int start) {
		this.start = start;
	}

}
