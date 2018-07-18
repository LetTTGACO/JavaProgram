package com.GluttonousSnake;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

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

	// 向上移动
	public void moveUp() {
		// 获取原来的蛇头
		Point head = snake.getFirst();
		// 添加新的蛇头
		snake.addFirst(new Point(head.x, head.y - 1));
		// 删除蛇尾
		snake.removeLast();
	}

	// 向下移动
	public void moveDown() {
		// 获取原来的蛇头
		Point head = snake.getFirst();
		// 添加新的蛇头
		snake.addFirst(new Point(head.x, head.y + 1));
		// 删除蛇尾
		snake.removeLast();
	}

	// 向左移动
	public void moveLeft() {
		// 获取原来的蛇头
		Point head = snake.getFirst();
		// 添加新的蛇头
		snake.addFirst(new Point(head.x - 1, head.y));
		// 删除蛇尾
		snake.removeLast();
	}

	// 向右移动
	public void moveRight() {
		// 获取原来的蛇头
		Point head = snake.getFirst();
		// 添加新的蛇头
		snake.addFirst(new Point(head.x + 1, head.y));
		// 删除蛇尾
		snake.removeLast();
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
		background[head.y][head.x] = '$';
		// 画蛇身
		for (int i = 1; i < snake.size(); i++) {
			Point body = snake.get(i);
			background[body.y][body.x] = '#';
		}
	}

	// 写一个方法,初始化地图
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

		// 向上移动三步
		for (int i = 0; i < 3; i++) {
			snake.moveUp();
			snake.refrash();
			Thread.sleep(500);
		}

		// 向左移动三步
		for (int i = 0; i < 3; i++) {
			snake.moveLeft();
			snake.refrash();
			Thread.sleep(500);
		}

		// 向下移动三步
		for (int i = 0; i < 3; i++) {
			snake.moveDown();
			snake.refrash();
			Thread.sleep(500);
		}

		// 向右移动三步
		for (int i = 0; i < 3; i++) {
			snake.moveRight();
			snake.refrash();
			Thread.sleep(500);
		}

	}
}
