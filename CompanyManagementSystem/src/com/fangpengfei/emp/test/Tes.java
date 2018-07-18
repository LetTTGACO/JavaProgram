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
        delMenItem.setText("  ɾ��  ");
        delMenItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                //�ò�����Ҫ������
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

	// ����Ҽ�����¼�
	private void TableClick(java.awt.event.MouseEvent evt) {
		// �ж��Ƿ�Ϊ����BUTTON3��ť��BUTTON3Ϊ����Ҽ�
		if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			// ͨ�����λ���ҵ����Ϊ����е���
			int focusedRowIndex = table.rowAtPoint(evt.getPoint());
			if (focusedRowIndex == -1) {
				return;
			}
			// �������ѡ����Ϊ��ǰ�Ҽ��������
			table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
			// �����˵�
			m_popupMenu.show(table, evt.getX(), evt.getY());
		}

	}

}
