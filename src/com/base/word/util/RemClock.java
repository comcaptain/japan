package com.base.word.util;

import java.util.GregorianCalendar;

public class RemClock {
	private long startTime;
	private long eclapsed;
	private boolean paused;
	private double speed;
	private long rest;

	public void start() {
		paused = false;
		this.startTime = GregorianCalendar.getInstance().getTimeInMillis();
	}
	public void pause() {
		eclapsed = getEclapsed();
		paused = true;
	}
	public String toString() {
		long timeInMillis = getEclapsed();
		return getTime(timeInMillis);
	}	
	private String getTime(long timeInMillis) {
		long time = timeInMillis / 1000;
		long second = time % 60;
		time /= 60;
		long minute = time % 60;
		long hour = time / 60;
		return String.format("%1$02d:%2$02d:%3$02d", hour, minute, second);	
	}
	public long getEclapsed() {
		return paused ? eclapsed : GregorianCalendar.getInstance().getTimeInMillis() - startTime + eclapsed;
	}
	public void predict(int total, int passed) {
		if (passed == 0) {
			speed = -1; rest = -1; return;
		}
		long eclapsed = getEclapsed();
		this.speed = eclapsed * 1.0 / passed;
		this.rest = (long) (speed * (total - passed));
	}
	public String getSpeed() {
		return getTime((long) this.speed);
	}
	public String getRest() {
		return getTime(this.rest);
	}
}
