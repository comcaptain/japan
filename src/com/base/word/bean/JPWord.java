package com.base.word.bean;

import java.io.Serializable;

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
