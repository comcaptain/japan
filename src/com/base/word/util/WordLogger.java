package com.base.word.util;

import java.util.HashMap;
import com.base.word.bean.JPWord;

public class WordLogger {
	public static String getWordStr(int[] wordFilter, JPWord word) {
		StringBuffer strBuff = new StringBuffer();
		for (int wordPart: wordFilter) {
			strBuff.append(word.getString(wordPart) + "  ");
		}
		return strBuff.toString();
	}
	public static String getWordStr(int[] wordFilter, JPWord word, HashMap<Integer, Integer> maxLengths) {
		StringBuffer strBuff = new StringBuffer();
		for (int wordPart: wordFilter) {
			String temp = word.getString(wordPart);
			int tempLength = getWordPartLength(temp);
			strBuff.append
			(rightAdd
				(temp, (maxLengths.get(wordPart) - tempLength + 4), ' ')
			);		
		}
		return strBuff.toString();
	}
	public static int getWordPartLength(String part) {
		if (part == null) return 4;
		int count = 0;
		for (char c: part.toCharArray()) {
			String temp = c + "";
			if (temp.matches("[0-9a-z\\sA-Z_*().'\";:\\-=&%$#!\\<>,+/\\[\\]]")) count++;
			else count += 2;
		}
		return count;
	}
	public static String rightAdd(String inputString, int count, char padChar) {
		StringBuffer result = new StringBuffer(inputString == null ? "null" : inputString);
		for (int i = 0; i < count; i++ ) {
			result.append(padChar);
		}
		return result.toString();
	}
	public static String getLevelStr(String level) {
		if (level == null) return JPWordConstants.LEVEL_SHOW[JPWordConstants.LEVEL_SHOW.length - 1];
		String result = "";
		for (char temp: level.toCharArray()) {
			int index = Integer.parseInt(temp + "");
			result += JPWordConstants.LEVEL_SHOW[index];
		}
		return result;
	}
}
