package com.base.word.bean;

import java.sql.SQLException;

import com.base.word.dao.JPWordDao;

public class WordSet {
	private JPWord[] wordList;
	private JPWordDao dao;
	public WordSet() {
		this.dao = new JPWordDao();
	}	
	public JPWord getWordById(int wordId) {
		for(JPWord word:wordList) {
			if (word.getWordId() == wordId) return word;
		}
		return null;
	}	
	public void filterList(String[] unitList, String[] levelList) throws SQLException {
		this.wordList = dao.getWordList(unitList, levelList);
	}
	public void filterWords(UserConfigBean userConfig) throws SQLException {
		JPWord[] temp = dao.getWordList(userConfig.getUnitList(), 
				userConfig.getLevelList());
		wordList = userConfig.isRandom() ? reOrderList(temp) : temp;
	}
	private JPWord[] reOrderList(JPWord[] words) {
		int upperBound = words.length + 1;
		while (--upperBound > 0) {
			int r = (int)(Math.random() * upperBound);
			JPWord temp = words[upperBound - 1];
			words[upperBound - 1] = words[r];
			words[r] = temp;
		}
		return words;
	}
	public JPWord[] getWords() {
		return this.wordList;
	}
	public void saveWord(JPWord word) throws SQLException {
		dao.saveWord(word);
	}
	public void setJPWord(JPWord word, String value) throws SQLException {
		word.setJpWord(value);
		dao.saveWord(word);
	}
	public void setCNWord(JPWord word, String value) throws SQLException {
		word.setCnWord(value);
		dao.saveWord(word);
	}
	public void setHanzi(JPWord word, String value) throws SQLException {
		word.setHanzi(value);
		dao.saveWord(word);
	}
	public void setLevel(JPWord word, String value) throws SQLException {
		word.setLevel(value);
		dao.saveWord(word);
	}
}
