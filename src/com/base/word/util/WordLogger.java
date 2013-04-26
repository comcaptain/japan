package com.base.word.util;

import com.base.word.JPWord;

public class WordLogger {
	public static String getWordStr(String wordFilter, JPWord word) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("【" + word.getWordId() + "】");
		strBuff.append("【" + word.getUnit() + "课】");
		strBuff.append("【" + getLevelStr(word.getLevel()) + "】");
		boolean allShow = wordFilter.indexOf('a') >= 0;
		if (allShow || wordFilter.indexOf('t') >= 0) strBuff.append("【" + word.getType() + "】");
		if (allShow || wordFilter.indexOf('j') >= 0) strBuff.append("【" + word.getJpWord() + "】");
		if (allShow || wordFilter.indexOf('h') >= 0) strBuff.append("【" + word.getHanzi() + "】");
		if (allShow || wordFilter.indexOf('c') >= 0) strBuff.append("【" + word.getCnWord() + "】");
		return strBuff.toString();
	}
	private static String getLevelStr(String level) {
		if (level == null) return JPWordConstants.LEVEL_SHOW[JPWordConstants.LEVEL_SHOW.length - 1];
		String result = "";
		for (char temp: level.toCharArray()) {
			int index = Integer.parseInt(temp + "");
			result += JPWordConstants.LEVEL_SHOW[index];
		}
		return result;
	}
}
