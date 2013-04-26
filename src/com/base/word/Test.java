package com.base.word;

import com.base.word.util.RemClock;

public class Test {
	public static void main(String[] args) {
		RemClock clock = new RemClock();
		clock.start();
		try {
			Thread.currentThread();
			Thread.sleep(1873);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(clock);
	}
}
