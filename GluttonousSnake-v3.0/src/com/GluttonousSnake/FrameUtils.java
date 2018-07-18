package com.GluttonousSnake;

import javax.swing.JFrame;

public class FrameUtils extends JFrame{
	public FrameUtils(){
		this.setTitle("测试");
		this.setSize(980, 640);//设置大小
		this.setLocationRelativeTo(null);//让窗体居中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//当关闭窗体时程序退出
		this.setVisible(true);
	}
}
