package com.base.word.bean;

import java.io.Serializable;

import com.base.word.util.JPWordConstants;
import com.base.word.util.WordLogger;

public class JPWord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 686654859467584617L;
	private int wordId = 0;
	private String jpWord;
	private String cnWord;
	private String hanzi;
	private String type;
	private String unit;
	//1-5
	private String level;
	public String getJpWord() {
		return jpWord;
	}
	public String getString(int type) {
		switch (type) {
		case JPWordConstants.WORD_ID: return wordId + "";
		case JPWordConstants.WORD_CN: return this.cnWord;
		case JPWordConstants.WORD_HANZI: return this.hanzi;
		case JPWordConstants.WORD_JP: return this.jpWord;
		case JPWordConstants.WORD_LEVEL: return WordLogger.getLevelStr(this.level);
		case JPWordConstants.WORD_TYPE: return this.type;
		case JPWordConstants.WORD_UNIT: return this.unit;
		}
		return null;
	}
	public void setJpWord(String jpWord) {
		this.jpWord = jpWord;
	}
	public String getCnWord() {
		return cnWord;
	}
	public void setCnWord(String cnWord) {
		this.cnWord = cnWord;
	}
	public String getHanzi() {
		return hanzi;
	}
	public void setHanzi(String hanzi) {
		this.hanzi = hanzi;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String toString() {
		return "【" + unit + "课】【" + jpWord + "】【" + hanzi + "】【" + type + "】【" + cnWord + "】";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
}
