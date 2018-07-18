package com.GluttonousSnake;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

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

	// �����ƶ�
	public void moveUp() {
		// ��ȡԭ������ͷ
		Point head = snake.getFirst();
		// ����µ���ͷ
		snake.addFirst(new Point(head.x, head.y - 1));
		// ɾ����β
		snake.removeLast();
	}

	// �����ƶ�
	public void moveDown() {
		// ��ȡԭ������ͷ
		Point head = snake.getFirst();
		// ����µ���ͷ
		snake.addFirst(new Point(head.x, head.y + 1));
		// ɾ����β
		snake.removeLast();
	}

	// �����ƶ�
	public void moveLeft() {
		// ��ȡԭ������ͷ
		Point head = snake.getFirst();
		// ����µ���ͷ
		snake.addFirst(new Point(head.x - 1, head.y));
		// ɾ����β
		snake.removeLast();
	}

	// �����ƶ�
	public void moveRight() {
		// ��ȡԭ������ͷ
		Point head = snake.getFirst();
		// ����µ���ͷ
		snake.addFirst(new Point(head.x + 1, head.y));
		// ɾ����β
		snake.removeLast();
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
		background[head.y][head.x] = '$';
		// ������
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			background[body.y][body.x] = '#';
		}
	}

	// дһ������,��ʼ����ͼ
	public void initBackground() {
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (rows == 0 || rows == HEIGHT - 1 || cols == 0 || cols == WIDTH - 1) {
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

		// �����ƶ�����
		for (int i = 0; i < 3; i++) {
			snake.moveUp();
			snake.refrash();
			Thread.sleep(500);
		}

		// �����ƶ�����
		for (int i = 0; i < 3; i++) {
			snake.moveLeft();
			snake.refrash();
			Thread.sleep(500);
		}

		// �����ƶ�����
		for (int i = 0; i < 3; i++) {
			snake.moveDown();
			snake.refrash();
			Thread.sleep(500);
		}

		// �����ƶ�����
		for (int i = 0; i < 3; i++) {
			snake.moveRight();
			snake.refrash();
			Thread.sleep(500);
		}

	}
}
