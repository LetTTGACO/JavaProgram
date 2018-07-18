package com.fangpengfei.emp.test;

import org.junit.Test;

import com.fangpengfei.emp.dao.EmpDao;
import com.fangpengfei.emp.entity.Emp;

public class EmpDaoTest {
	EmpDao empDao = new EmpDao();
	
	@Test
	public void testAddDept(){
		empDao.addEmp(new Emp(5, "»ÊÊÜ", "ÄÐ", "±³¾°", 30000, 1));
	}
	
	@Test
	public void testGetOneDept(){
		System.out.println(empDao.getOneEmp(5));
	}
	
	@Test
	public void testDeleteDept(){
		empDao.deleteEmp(6);
	}
	
	@Test
	public void testUpdateDept(){
		empDao.updateEmp(new Emp(1, "»ÊÊÜ111", "ÄÐ", "±³¾°", 30000, 50));
	}
	
	@Test
	public void testGetAllDept(){
		System.out.println(empDao.getAllEmp());
	}
}
