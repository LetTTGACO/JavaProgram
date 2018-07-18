package com.fangpengfei.emp.test;

import java.sql.SQLException;

import org.junit.Test;

import com.fangpengfei.emp.utils.JDBCUtils;

public class TestJDBCUtils {
	@Test
	public void testGetConnection() throws Exception{
		System.out.println(JDBCUtils.getConnection().getCatalog());
	}
}
