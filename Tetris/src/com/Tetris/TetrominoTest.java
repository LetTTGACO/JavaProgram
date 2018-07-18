package com.Tetris;


import org.junit.Test;

public class TetrominoTest {

	@Test
	public void test() {
		Tetromino t = new O();
		t.spin();
		System.out.println(10*20);
	}

}
