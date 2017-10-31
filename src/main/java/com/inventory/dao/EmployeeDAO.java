package com.inventory.dao;


import com.inventory.entity.Employee;

public interface EmployeeDAO extends BaseDAO<Employee, String> {
	Employee login(Employee emp);

	Employee findLastOne();
}
