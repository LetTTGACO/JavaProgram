package com.GluttonousSnake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SnakeView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3490856585755129017L;

	// ��ͼ�Ŀ�(����)
	public static int WIDTH = 16;

	// ��ͼ��(����)
	public static int HEIGHT = 10;

	// ������ӵĿ��
	public static final int CELLWIDTH = 60;
	public static final int CELLHEIGHT = 60;

	// �����ͼ
	// 1�����ͼ
	private static boolean[][] background = new boolean[HEIGHT][WIDTH];

	// ��ʼ����ͼ
	public void initBackground() {
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (rows == 0 || rows == HEIGHT - 1) {
					background[rows][cols] = true;
				}
			}
		}
	}

	// ������
	static LinkedList<Point> snake = new LinkedList<Point>();

	// ��ʼ����
	public void initSnake() {
		int x = WIDTH / 2;
		int y = HEIGHT / 2;
		snake.addFirst(new Point(x - 1, y));
		snake.addFirst(new Point(x, y));
		snake.addFirst(new Point(x + 1, y));// ��ͷ
	}

	// ����ʳ��
	private static Point food;

	// ��ȡ�ز�
	Image headImage = new ImageIcon(".\\src\\com\\GluttonousSnake\\headImage.jpg").getImage();
	Image bodyImage = new ImageIcon(".\\src\\com\\GluttonousSnake\\bodyImage.png").getImage();
	Image foodImage = new ImageIcon(".\\src\\com\\GluttonousSnake\\foodImage.jpg").getImage();

	// ��¼��Ϸ�Ƿ����
	static boolean isGameOver = false;// Ĭ����Ϸû�н���

	// ��Ϸ�����ķ���
	public void isGameOver() {
		// ײǽ����
		Point head = snake.getFirst();
		if (background[head.y][head.x]) {
			isGameOver = true;
		}

		// ҧ���Լ� ����
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			if (head.equals(body)) {
				isGameOver = true;
			}
		}
	}

	// ����ʳ��
	public static void createFood() {
		// ����һ�����������
		Random random = new Random();
		while (true) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			if (background[y][x] != true) {
				food = new Point(x, y);
				break;
			}
		}
	}

	// ��ʳ��
	public static boolean eatFood() {
		// ��ȡԭ������ͷ
		Point head = snake.getFirst();
		if (food.equals(head)) {
			return true;
		}
		return false;
	}

	// ���ĸ�������ʾ�ĸ�����
	public static final int UP_DIRECTION = 1;
	public static final int DOWN_DIRECTION = -1;
	public static final int LEFT_DIRECTION = 2;
	public static final int RIGHT_DIRECTION = -2;

	// �ߵ�ǰ�ķ���
	int currentDrection = -2;// Ĭ��������

	// �ı䵱ǰ����ķ���
	public void changeDirection(int newDirection) {
		// �ж��·����뵱ǰ�������෴����,�������,������ı�
		if (newDirection + currentDrection != 0) {
			this.currentDrection = newDirection;
		}
	}

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

	@Override
	public void paint(Graphics g) {// Graphics g��һ������,�������κζ���
		// ����ͼ
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (background[rows][cols]) {
					// ʯͷ
					g.setColor(Color.GRAY);
				} else {
					// �յ�
					g.setColor(Color.WHITE);
				}
				// ��Ҫ������
				g.fill3DRect(cols * CELLWIDTH, rows * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);

			}
		}

		// ����
		// ������
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			g.drawImage(bodyImage, body.x * CELLWIDTH, body.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, this);
		}
		// ����ͷ
		Point head = snake.getFirst();
		g.drawImage(headImage, head.x * CELLWIDTH, head.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, this);
		// ��ʾʳ��
		g.setColor(Color.ORANGE);
		g.drawImage(foodImage, food.x * CELLWIDTH, food.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, this);

	}

	public static void main(String[] args) {
		FrameUtils frame = new FrameUtils();
		final SnakeView snakeView = new SnakeView();
		snakeView.initBackground();
		snakeView.initSnake();
		SnakeView.createFood();
		frame.add(snakeView);

		// ����������¼�����
		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_UP:
					snakeView.changeDirection(UP_DIRECTION);
					snakeView.repaint();// ʵ���Ͼ��ǵ�����paint�ķ���
					break;
				case KeyEvent.VK_DOWN:
					snakeView.changeDirection(DOWN_DIRECTION);
					snakeView.repaint();// ʵ���Ͼ��ǵ�����paint�ķ���
					break;
				case KeyEvent.VK_LEFT:
					snakeView.changeDirection(LEFT_DIRECTION);
					snakeView.repaint();// ʵ���Ͼ��ǵ�����paint�ķ���
					break;
				case KeyEvent.VK_RIGHT:
					snakeView.changeDirection(RIGHT_DIRECTION);
					snakeView.repaint();// ʵ���Ͼ��ǵ�����paint�ķ���
					break;
				default:
					break;
				}
				snakeView.move();
				snakeView.isGameOver();
				snakeView.repaint();
				if (isGameOver) {
					System.exit(0);
					// g.setColor(Color.BLUE);
					// //���û��ʵ�����
					// g.setFont(new Font("����", Font.BOLD, 30));
					// g.drawString("GAME OVER", 300, 200);
				}
			}
		});

	}
}
