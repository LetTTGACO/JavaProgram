package com.fangpengfei.emp.test;

import java.util.List;

import org.junit.Test;

import com.fangpengfei.emp.dao.UserDao;
import com.fangpengfei.emp.entity.User;

public class UserDaoTest {
	UserDao dao = new UserDao();
	@Test
	public void testAddUser(){
		dao.addUser(new User("admin1", "abc123", "管理员1"));
	}
	
	@Test
	public void testGetOneUser(){
		User user = dao.getOneUser("admin1");
		System.out.println(user);
	}
	
	@Test
	public void testDeleteUser(){
		dao.deleteUser("admin3");
	}
	
	@Test
	public void testUpdateUser(){
		dao.updateUser(new User("admin1", "123456789", "超级管理员"));
	}
	
	@Test
	public void testGetAllUser(){
		List<User> allUser = dao.getAllUser();
		System.out.println(allUser);
	}
	
}
