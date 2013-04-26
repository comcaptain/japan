package com.base.word.bean;

public class UserConfigBean {
	private boolean random = true;
	private String[] unitList;
	private String[] levelList;
	private String wordFilter;
	public boolean isRandom() {
		return random;
	}
	public void setRandom(boolean random) {
		this.random = random;
	}
	public String[] getUnitList() {
		return unitList;
	}
	public void setUnitList(String[] unitList) {
		this.unitList = unitList;
	}
	public String[] getLevelList() {
		return levelList;
	}
	public void setLevelList(String[] levelList) {
		this.levelList = levelList;
	}
	public String getWordFilter() {
		return wordFilter;
	}
	public void setWordFilter(String wordFilter) {
		this.wordFilter = wordFilter;
	}
}
