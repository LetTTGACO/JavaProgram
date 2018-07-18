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

	// 地图的宽(列数)
	public static int WIDTH = 16;

	// 地图高(行数)
	public static int HEIGHT = 10;

	// 定义格子的宽高
	public static final int CELLWIDTH = 60;
	public static final int CELLHEIGHT = 60;

	// 定义地图
	// 1代表地图
	private static boolean[][] background = new boolean[HEIGHT][WIDTH];

	// 初始化地图
	public void initBackground() {
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (rows == 0 || rows == HEIGHT - 1) {
					background[rows][cols] = true;
				}
			}
		}
	}

	// 定义蛇
	static LinkedList<Point> snake = new LinkedList<Point>();

	// 初始化蛇
	public void initSnake() {
		int x = WIDTH / 2;
		int y = HEIGHT / 2;
		snake.addFirst(new Point(x - 1, y));
		snake.addFirst(new Point(x, y));
		snake.addFirst(new Point(x + 1, y));// 蛇头
	}

	// 定义食物
	private static Point food;

	// 读取素材
	Image headImage = new ImageIcon(".\\src\\com\\GluttonousSnake\\headImage.jpg").getImage();
	Image bodyImage = new ImageIcon(".\\src\\com\\GluttonousSnake\\bodyImage.png").getImage();
	Image foodImage = new ImageIcon(".\\src\\com\\GluttonousSnake\\foodImage.jpg").getImage();

	// 记录游戏是否结束
	static boolean isGameOver = false;// 默认游戏没有结束

	// 游戏结束的方法
	public void isGameOver() {
		// 撞墙死亡
		Point head = snake.getFirst();
		if (background[head.y][head.x]) {
			isGameOver = true;
		}

		// 咬到自己 蛇身
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			if (head.equals(body)) {
				isGameOver = true;
			}
		}
	}

	// 生成食物
	public static void createFood() {
		// 创建一个随机数对象
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

	// 吃食物
	public static boolean eatFood() {
		// 获取原来的蛇头
		Point head = snake.getFirst();
		if (food.equals(head)) {
			return true;
		}
		return false;
	}

	// 用四个常量表示四个方向
	public static final int UP_DIRECTION = 1;
	public static final int DOWN_DIRECTION = -1;
	public static final int LEFT_DIRECTION = 2;
	public static final int RIGHT_DIRECTION = -2;

	// 蛇当前的方向
	int currentDrection = -2;// 默认向右走

	// 改变当前方向的方法
	public void changeDirection(int newDirection) {
		// 判断新方向与当前方向是相反方向,如果不是,才允许改变
		if (newDirection + currentDrection != 0) {
			this.currentDrection = newDirection;
		}
	}

	// 蛇移动的方法

	public void move() {
		Point head = snake.getFirst();
		// 蛇是根据当前方向移动的
		switch (currentDrection) {
		case UP_DIRECTION:
			// 添加新的蛇头
			snake.addFirst(new Point(head.x, head.y - 1));
			break;
		case DOWN_DIRECTION:
			// 添加新的蛇头
			snake.addFirst(new Point(head.x, head.y + 1));
			break;
		case LEFT_DIRECTION:
			if (head.x == 0) {
				snake.addFirst(new Point(WIDTH - 1, head.y));
			} else {
				// 添加新的蛇头
				snake.addFirst(new Point(head.x - 1, head.y));
			}
			break;
		case RIGHT_DIRECTION:
			if (head.x == WIDTH - 1) {
				snake.addFirst(new Point(0, head.y));
			} else {
				// 添加新的蛇头
				snake.addFirst(new Point(head.x + 1, head.y));
			}

			break;
		default:
			break;
		}

		if (eatFood()) {
			// 吃到了食物
			createFood();
		} else {
			// 删除蛇尾
			snake.removeLast();
		}

	}

	@Override
	public void paint(Graphics g) {// Graphics g是一个画笔,用来画任何东西
		// 画地图
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				if (background[rows][cols]) {
					// 石头
					g.setColor(Color.GRAY);
				} else {
					// 空地
					g.setColor(Color.WHITE);
				}
				// 都要画矩形
				g.fill3DRect(cols * CELLWIDTH, rows * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);

			}
		}

		// 画蛇
		// 画蛇身
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			g.drawImage(bodyImage, body.x * CELLWIDTH, body.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, this);
		}
		// 画蛇头
		Point head = snake.getFirst();
		g.drawImage(headImage, head.x * CELLWIDTH, head.y * CELLHEIGHT, CELLWIDTH, CELLHEIGHT, this);
		// 显示食物
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

		// 给窗口添加事件监听
		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_UP:
					snakeView.changeDirection(UP_DIRECTION);
					snakeView.repaint();// 实际上就是调用了paint的方法
					break;
				case KeyEvent.VK_DOWN:
					snakeView.changeDirection(DOWN_DIRECTION);
					snakeView.repaint();// 实际上就是调用了paint的方法
					break;
				case KeyEvent.VK_LEFT:
					snakeView.changeDirection(LEFT_DIRECTION);
					snakeView.repaint();// 实际上就是调用了paint的方法
					break;
				case KeyEvent.VK_RIGHT:
					snakeView.changeDirection(RIGHT_DIRECTION);
					snakeView.repaint();// 实际上就是调用了paint的方法
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
					// //设置画笔的字体
					// g.setFont(new Font("宋体", Font.BOLD, 30));
					// g.drawString("GAME OVER", 300, 200);
				}
			}
		});

	}
}
