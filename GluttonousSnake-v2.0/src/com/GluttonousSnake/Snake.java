package com.GluttonousSnake;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Snake {

	// ��ͼ�Ŀ�(����)
	public static int WIDTH = 35;

	// ��ͼ��(����)
	public static int HEIGHT = 9;

	// ��ͼ
	private char[][] background = new char[HEIGHT][WIDTH];

	// ���м���,ʹ�ü��ϱ����߽ڵ��������Ϣ
	// LinkedList ��ɾ�ٶȿ�,�䷽���ʺ�
	LinkedList<Point> snake = new LinkedList<Point>();

	// ʳ��
	Point food;

	// ���ĸ�������ʾ�ĸ�����
	public static final int UP_DIRECTION = 1;
	public static final int DOWN_DIRECTION = -1;
	public static final int LEFT_DIRECTION = 2;
	public static final int RIGHT_DIRECTION = -2;

	// �ߵ�ǰ�ķ���
	int currentDrection = -2;// Ĭ��������

	// ��¼��Ϸ�Ƿ����
	boolean isGameOver = false;// Ĭ����Ϸû�н���

	// ���ƶ��ķ���
	public void move() {
		Point head = snake.getFirst();
		// ���Ǹ��ݵ�ǰ�����ƶ���
		switch (currentDrection) {
		case UP_DIRECTION:
			// ����µ���ͷ
			snake.addFirst(new Point(head.x, head.y - 1));
			break;
		case DOWN_DIRECTION:
			// ����µ���ͷ
			snake.addFirst(new Point(head.x, head.y + 1));
			break;
		case LEFT_DIRECTION:
			if (head.x == 0) {
				snake.addFirst(new Point(WIDTH - 1, head.y));
			} else {
				// ����µ���ͷ
				snake.addFirst(new Point(head.x - 1, head.y));
			}
			break;
		case RIGHT_DIRECTION:
			if (head.x == WIDTH - 1) {
				snake.addFirst(new Point(0, head.y));
			} else {
				// ����µ���ͷ
				snake.addFirst(new Point(head.x + 1, head.y));
			}

			break;
		default:
			break;
		}

		if (eatFood()) {
			// �Ե���ʳ��
			createFood();
		} else {
			// ɾ����β
			snake.removeLast();
		}

	}

	// �ı䵱ǰ����ķ���
	public void changeDirection(int newDirection) {
		// �ж��·����뵱ǰ�������෴����,�������,������ı�
		if (newDirection + currentDrection != 0) {
			this.currentDrection = newDirection;
		}
	} 

	// ����ʳ��
	public void createFood() {
		// ����һ�����������
		Random random = new Random();
		while (true) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			if (background[y][x] != '*' && background[y][x] != '#' && background[y][x] != '$') {
				food = new Point(x, y);
				break;
			}
		}
	}

	// ��ʾʳ��
	public void showFood() {
		background[food.y][food.x] = '@';
	}

	// ��ʼ����
	public void initSnake() {
		int x = WIDTH / 2;
		int y = HEIGHT / 2;
		snake.addFirst(new Point(x - 1, y));
		snake.addFirst(new Point(x, y));
		snake.addFirst(new Point(x + 1, y));// ��ͷ
	}

	// ��ʾ��----->���߽ڵ��������Ϣ��������ͼ��,�ڵ�ͼ�ϻ��϶�Ӧ���ַ�
	public void showSnake() {
		// ȡ����ͷ
		Point head = snake.getFirst();
		// ������
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			background[body.y][body.x] = '#';
		}
		//����ͷ
		background[head.y][head.x] = '$';
	}

	// дһ������,��ʼ����ͼ
	public void initBackground() {
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (rows == 0 || rows == HEIGHT - 1) {
					background[rows][cols] = '*';
				} else {
					background[rows][cols] = ' ';
				}
			}
		}
	}

	// ��ʾ��ͼ
	private void showBackground() {
		// ��ӡ��ά����
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				System.out.print(background[rows][cols]);
			}
			System.out.println();// ����
		}
	}

	// ˢ����Ϸ��״̬
	public void refrash() {
		// �����Ϸ֮ǰ��������Ϣ
		initBackground();
		// �������µ���Ϣ��������ͼ��
		showSnake();
		// ��ʳ�����Ϣ��������ͼ��
		showFood();
		// ��ʾ��ǰ��ͼ����Ϣ
		showBackground();
	}

	// ��ʳ��
	public boolean eatFood() {
		// ��ȡԭ������ͷ
		Point head = snake.getFirst();
		if (food.equals(head)) {
			return true;
		}
		return false;
	}

	// ��Ϸ�����ķ���
	public void isGameOver() {

		Point head = snake.getFirst();
		// ײǽ����
		if (background[head.y][head.x] == '*') {
			isGameOver = true;
//			Snake snake = new Snake();
//			
		}
		// ҧ���Լ�
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			if (head.equals(body)) {
				isGameOver = true;
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		Snake snake = new Snake();
		// ��ʼ����ͼ
		snake.initBackground();
		// ��ʼ����
		snake.initSnake();
		// ���߽ڵ��������Ϣ��������ͼ��
		snake.showSnake();
		// ��ʼ��ʳ��
		snake.createFood();
		// ��ʾʳ��
		snake.showFood();
		// ��ʾ��ͼ
		snake.showBackground();

		for (int i = 0; i < 100; i++) {
			snake.changeDirection(UP_DIRECTION);
			snake.move();
			snake.isGameOver();//�ж���Ϸ�Ƿ����
			snake.refrash();
			if (snake.isGameOver) {
				System.out.println("��Ϸ����");
				System.exit(0);;
			}
//			snake.refrash();
			Thread.sleep(300);
		}

		// JFrame frame = new JFrame("������");
		// frame.add(new JButton("��"),BorderLayout.NORTH);
		// frame.add(new JButton("��"),BorderLayout.SOUTH);
		// frame.add(new JButton("��"),BorderLayout.WEST);
		// frame.add(new JButton("��"),BorderLayout.EAST);
		// JButton button = new JButton("������Ʒ���");
		// button.addKeyListener(new KeyAdapter() {
		//
		// @Override
		// public void keyPressed(KeyEvent e) {
		// // TODO Auto-generated method stub
		// super.keyPressed(e);
		// }
		//
		// });
		// frame.add(button);

	}
}
