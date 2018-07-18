package com.fangpengfei.emp.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.table.TableModel;

import com.fangpengfei.emp.view.DeptPanel;
import com.fangpengfei.emp.view.DeptTableModel;
import com.mysql.cj.xdevapi.Table;

public class Tes {
	private JPopupMenu m_popupMenu;
	private JMenuItem delMenItem;
	private DeptPanel deptPanel;
	private JTable table;
	
	
	
	private void createPopupMenu(){
		table = new JTable(new DeptTableModel());
        m_popupMenu = new JPopupMenu();
        delMenItem = new JMenuItem();
        delMenItem.setText("  删除  ");
        delMenItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                //该操作需要做的事
            }
       });
        m_popupMenu.add(delMenItem);
	
	
	
        table.addMouseListener(new java.awt.event.MouseAdapter() {

		public void mouseClicked(java.awt.event.MouseEvent evt) {
			jTable1MouseClicked(evt);
		}

	});

	}
	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {

		TableClick(evt);
	}

	// 鼠标右键点击事件
	private void TableClick(java.awt.event.MouseEvent evt) {
		// 判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
		if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			// 通过点击位置找到点击为表格中的行
			int focusedRowIndex = table.rowAtPoint(evt.getPoint());
			if (focusedRowIndex == -1) {
				return;
			}
			// 将表格所选项设为当前右键点击的行
			table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
			// 弹出菜单
			m_popupMenu.show(table, evt.getX(), evt.getY());
		}

	}

}
