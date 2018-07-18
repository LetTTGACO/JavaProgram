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

	// 地图的宽(列数)
	public static int WIDTH = 35;

	// 地图高(行数)
	public static int HEIGHT = 9;

	// 地图
	private char[][] background = new char[HEIGHT][WIDTH];

	// 单列集合,使用集合保存蛇节点的所有信息
	// LinkedList 增删速度快,其方法适合
	LinkedList<Point> snake = new LinkedList<Point>();

	// 食物
	Point food;

	// 用四个常量表示四个方向
	public static final int UP_DIRECTION = 1;
	public static final int DOWN_DIRECTION = -1;
	public static final int LEFT_DIRECTION = 2;
	public static final int RIGHT_DIRECTION = -2;

	// 蛇当前的方向
	int currentDrection = -2;// 默认向右走

	// 记录游戏是否结束
	boolean isGameOver = false;// 默认游戏没有结束

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

	// 改变当前方向的方法
	public void changeDirection(int newDirection) {
		// 判断新方向与当前方向是相反方向,如果不是,才允许改变
		if (newDirection + currentDrection != 0) {
			this.currentDrection = newDirection;
		}
	} 

	// 生成食物
	public void createFood() {
		// 创建一个随机数对象
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

	// 显示食物
	public void showFood() {
		background[food.y][food.x] = '@';
	}

	// 初始化蛇
	public void initSnake() {
		int x = WIDTH / 2;
		int y = HEIGHT / 2;
		snake.addFirst(new Point(x - 1, y));
		snake.addFirst(new Point(x, y));
		snake.addFirst(new Point(x + 1, y));// 蛇头
	}

	// 显示蛇----->将蛇节点的坐标信息反馈到地图上,在地图上画上对应的字符
	public void showSnake() {
		// 取出蛇头
		Point head = snake.getFirst();
		// 画蛇身
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			background[body.y][body.x] = '#';
		}
		//画蛇头
		background[head.y][head.x] = '$';
	}

	// 写一个方法,初始化地图
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

	// 显示地图
	private void showBackground() {
		// 打印二维数组
		for (int rows = 0; rows < background.length; rows++) {
			for (int cols = 0; cols < background[rows].length; cols++) {
				System.out.print(background[rows][cols]);
			}
			System.out.println();// 换行
		}
	}

	// 刷新游戏的状态
	public void refrash() {
		// 清空游戏之前的所有信息
		initBackground();
		// 把蛇最新的信息反馈到地图上
		showSnake();
		// 把食物的信息反馈到地图上
		showFood();
		// 显示当前地图的信息
		showBackground();
	}

	// 吃食物
	public boolean eatFood() {
		// 获取原来的蛇头
		Point head = snake.getFirst();
		if (food.equals(head)) {
			return true;
		}
		return false;
	}

	// 游戏结束的方法
	public void isGameOver() {

		Point head = snake.getFirst();
		// 撞墙死亡
		if (background[head.y][head.x] == '*') {
			isGameOver = true;
//			Snake snake = new Snake();
//			
		}
		// 咬到自己
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			if (head.equals(body)) {
				isGameOver = true;
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		Snake snake = new Snake();
		// 初始化地图
		snake.initBackground();
		// 初始化蛇
		snake.initSnake();
		// 将蛇节点的坐标信息反馈到地图上
		snake.showSnake();
		// 初始化食物
		snake.createFood();
		// 显示食物
		snake.showFood();
		// 显示地图
		snake.showBackground();

		for (int i = 0; i < 100; i++) {
			snake.changeDirection(UP_DIRECTION);
			snake.move();
			snake.isGameOver();//判断游戏是否结束
			snake.refrash();
			if (snake.isGameOver) {
				System.out.println("游戏结束");
				System.exit(0);;
			}
//			snake.refrash();
			Thread.sleep(300);
		}

		// JFrame frame = new JFrame("方向盘");
		// frame.add(new JButton("↑"),BorderLayout.NORTH);
		// frame.add(new JButton("↓"),BorderLayout.SOUTH);
		// frame.add(new JButton("←"),BorderLayout.WEST);
		// frame.add(new JButton("→"),BorderLayout.EAST);
		// JButton button = new JButton("点击控制方向");
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
