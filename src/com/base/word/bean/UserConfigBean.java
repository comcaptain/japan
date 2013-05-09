package com.base.word.bean;

import java.util.LinkedList;
import java.util.List;

import com.base.word.util.JPWordConstants;

public class UserConfigBean {
	private boolean random = true;
	private String[] unitList;
	private String[] levelList;
	private int[] wordFilter;
	public static final int[] wordFilterAll = {JPWordConstants.WORD_ID, JPWordConstants.WORD_LEVEL, JPWordConstants.WORD_UNIT, 
		JPWordConstants.WORD_TYPE, JPWordConstants.WORD_JP, JPWordConstants.WORD_HANZI, JPWordConstants.WORD_CN };
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
	public int[] getWordFilter() {
		return wordFilter;
	}
	public void setWordFilter(String wordFilterStr) {
		List<Integer> parts = new LinkedList<Integer>();
		parts.add(JPWordConstants.WORD_ID);
		parts.add(JPWordConstants.WORD_LEVEL);
		parts.add(JPWordConstants.WORD_UNIT);
		boolean allShow = wordFilterStr.indexOf('a') >= 0;
		if (allShow || wordFilterStr.indexOf('t') >= 0) parts.add(JPWordConstants.WORD_TYPE);
		if (allShow || wordFilterStr.indexOf('j') >= 0) parts.add(JPWordConstants.WORD_JP);
		if (allShow || wordFilterStr.indexOf('h') >= 0) parts.add(JPWordConstants.WORD_HANZI);
		if (allShow || wordFilterStr.indexOf('c') >= 0) parts.add(JPWordConstants.WORD_CN);
		this.wordFilter = new int[parts.size()];
		int i = 0;
		for (Integer part: parts) {
			wordFilter[i++] = part.intValue();
		}
	}
}
