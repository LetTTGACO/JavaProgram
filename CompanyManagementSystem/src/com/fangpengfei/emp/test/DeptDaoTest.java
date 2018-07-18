package com.fangpengfei.emp.test;

import org.junit.Test;

import com.fangpengfei.emp.dao.DeptDao;
import com.fangpengfei.emp.entity.Dept;

public class DeptDaoTest {
	DeptDao deptDao = new DeptDao();
	
	@Test
	public void testAddDept(){
		deptDao.addDpet(new Dept(0, "研发部"));
	}
	
	@Test
	public void testGetOneDept(){
		Dept oneDept = deptDao.getOneDept(1);
		System.out.println(oneDept);
	}
	
	@Test
	public void testupdateDept(){
		deptDao.updateDept(new Dept(1, "体育部"));
	}
	
	@Test
	public void testdeleteDept(){
		deptDao.deleteDept(2);
	}
	
	@Test
	public void testGetDeptCount(){
		System.out.println(deptDao.getDeptCount(1));
	}
	
	
}
