package com.fangpengfei.emp.test;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PopupMenuDemo extends JFrame implements ActionListener {
	JButton btnTest = new JButton("Test");
	JTextArea textArea = new JTextArea();
	PopupMenu pMenu = new PopupMenu(); // ��������ʽ�˵������������ǲ˵���
	MenuItem mItemCopy = new MenuItem("����");
	MenuItem mItemPaste = new MenuItem("ճ��");
	MenuItem mItemCut = new MenuItem("����");
	MouseAdapter mouseAdapter=new MouseAdapter()//��������¼�
		{
			public void mouseClicked(MouseEvent event)
			{
				if(event.getButton()==MouseEvent.BUTTON3)//ֻ��Ӧ����Ҽ������¼�
				{
					pMenu.show(textArea,event.getX(),event.getY());// �����λ����ʾ����ʽ�˵�
	}}};
	ActionListener menuAction=new ActionListener()// ��Ӧ�����˵�����¼���ֻ��ʾ����
	{// �������ݿ��Լ���д
	public void actionPerformed(ActionEvent e){MenuItem item=(MenuItem)e.getSource();if(item==mItemCopy) // �����ˡ����ơ��˵���
	{JOptionPane.showMessageDialog(null,"����");}else if(item==mItemPaste) // ��ճ�����˵���
	{JOptionPane.showMessageDialog(null,"ճ��");

	}else{JOptionPane.showMessageDialog(null,"����"); // �����С��˵���
	}}};

	public PopupMenuDemo() {
		setTitle("Test");
		setSize(300, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(btnTest, BorderLayout.NORTH);
		add(textArea, BorderLayout.CENTER);
		textArea.add(pMenu); // ����ʽ�˵����뵽�ı����У���������ʾ
		textArea.addMouseListener(mouseAdapter); // �ı��������������
		pMenu.add(mItemCopy); // �˵���ĵ����¼�������
		mItemCopy.addActionListener(menuAction);
		pMenu.add(mItemPaste);
		mItemPaste.addActionListener(menuAction);
		pMenu.add(mItemCut);
		mItemCut.addActionListener(menuAction);

		btnTest.addActionListener(this);
	}

	public static void main(String... args) {
		new PopupMenuDemo().setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "���Թ���");
	}

}
