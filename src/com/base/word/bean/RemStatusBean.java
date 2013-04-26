package com.base.word.bean;

import com.base.word.util.RemClock;

public class RemStatusBean {
	private int[] levelCount = new int[10];
	private UserConfigBean userConfig;
	private RemClock clock;
	private WordSet wordSet;
	public int getLevelCountLength() {
		return levelCount.length;
	}
	public void addLevelCount(int level) {
		levelCount[level]++;
	}
	public int getLevelCount(int level) {
		return levelCount[level];
	}
	public UserConfigBean getUserConfig() {
		return userConfig;
	}
	public void setUserConfig(UserConfigBean userConfig) {
		this.userConfig = userConfig;
	}
	public RemClock getClock() {
		return clock;
	}
	public void setClock(RemClock clock) {
		this.clock = clock;
	}
	public WordSet getWordSet() {
		return wordSet;
	}
	public void setWordSet(WordSet wordSet) {
		this.wordSet = wordSet;
	}
}
