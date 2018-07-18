package com.fangpengfei.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fangpengfei.emp.entity.Dept;
import com.fangpengfei.emp.entity.Emp;
import com.fangpengfei.emp.exception.EmpException;
import com.fangpengfei.emp.utils.JDBCUtils;

public class DeptDao {
	// ���Ӳ���
	public void addDpet(Dept dept) {
		// �Բ������ƽ��зǿ��ж�
		if (null == dept.getName() || "".equals(dept.getName().trim())) {
			throw new EmpException("�������Ʋ���Ϊ��");
		}
		// �Բ������ƽ����ظ��ж�
		List<Dept> allDept = this.getAllDept();
		for (Dept depts : allDept) {
			if (dept.getId() ==depts.getId()) {
				throw new EmpException("����ID�����ظ�");
			} else if (dept.getName().equals(depts.getName())) {
				throw new EmpException("�������Ʋ����ظ�");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "insert into dept values (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dept.getId());
			pstmt.setString(2, dept.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// ��ѯһ������
	public Dept getOneDept(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from dept where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int deptid = rs.getInt("id");
				String deptname = rs.getString("name");
				return new Dept(deptid, deptname);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}
		return null;
	}

	// ɾ������
	public void deleteDept(int id) {
		// �ж�ɾ���Ĳ����Ƿ����
		Dept dept = this.getOneDept(id);
		if (dept == null) {
			throw new EmpException("ɾ���Ĳ��Ų�����");
		}
		// �жϲ����л���Ա��,�������Ա������ɾ��
		EmpDao empDao = new EmpDao();
		List<Emp> allEmp = empDao.getAllEmp();
		for (Emp emp : allEmp) {
			if (emp.getDetpId() == id) {
				throw new EmpException("ɾ���Ĳ��Ż���Ա��");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "delete from dept where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// �޸Ĳ���
	public void updateDept(Dept dept) {
		// �жϲ������Ƿ�Ϊ��
		String name = dept.getName();
		if (name == null || "".equals(name.trim())) {
			throw new EmpException("����������Ϊ��");
		}
		// �жϲ����Ƿ��Ѵ���
		Dept oneDept = this.getOneDept(dept.getId());
		if (null == oneDept) {
			throw new EmpException("�޸ĵĲ��Ų�����");
		}
		List<Dept> depts = this.getAllDept();
		for (Dept dept2 : depts) {
			if (dept2.getName().equals(dept.getName())) {
				throw new EmpException("�޸ĵĲ����Ѵ���");
			}
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "update dept set name = ? where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dept.getName());
			pstmt.setInt(2, dept.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt);
		}
	}

	// ��ѯ���в���
	public List<Dept> getAllDept() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ׼��һ���ռ���
		List<Dept> depts;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select * from dept";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			depts = new ArrayList<Dept>();
			while (rs.next()) {
				int deptid = rs.getInt("id");
				String deptname = rs.getString("name");
				depts.add(new Dept(deptid, deptname));
			}
			return depts;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}

	}

	// ��ѯ����Ա������
	public int getDeptCount(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select count(*) as empcount from emp where dept_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int empcount = rs.getInt("empcount");
				return empcount;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JDBCUtils.close(conn, pstmt, rs);
		}
		return 0;
	}
}
