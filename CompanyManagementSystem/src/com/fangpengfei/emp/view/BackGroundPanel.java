package com.fangpengfei.emp.view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackGroundPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1091693511362369765L;
	private Image image ;
    public void paint(Graphics graphics){
        try {
            image=ImageIO.read(new File("D:\\eclipse-workspace\\emp\\src\\com\\fangpengfei\\emp\\images\\beidou.jpg"));
            graphics.drawImage(image, 0, 0, 300, 300, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
