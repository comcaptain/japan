package com.base.word.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.base.word.bean.JPWord;
import com.base.word.bean.RemStatusBean;
import com.base.word.bean.UserConfigBean;
import com.base.word.bean.WordSet;
import com.base.word.dao.JPWordDao;

public class CommandExecutor {
	private WordSet wordSet;
	private Map<String, Integer> paramCounts = new HashMap<String, Integer>();
	private UserConfigBean userConfig;
	private JPWordDao dao;
	

	public CommandExecutor(WordSet wordSet, UserConfigBean userConfig) {
		this.dao = new JPWordDao();
		this.userConfig = userConfig;
		this.wordSet = wordSet;
		this.paramCounts.put("-f", 1);
		this.paramCounts.put("-fn", 1);
		this.paramCounts.put("-t", 0);
		this.paramCounts.put("-sj", -2);
		this.paramCounts.put("-sh", -2);
		this.paramCounts.put("-sc", -2);
		this.paramCounts.put("-sl", -2);
		this.paramCounts.put("-st", -2);
		this.paramCounts.put("ls", 0);
		this.paramCounts.put("s", 0);
		this.paramCounts.put("exit", 0);
		this.paramCounts.put("num", 0);
		this.paramCounts.put("pause", 0);
		this.paramCounts.put("start", 0);
		this.paramCounts.put("a", 0);
		this.paramCounts.put("count", 0);
	}
	
	public int execute(String inputStr, JPWord word, RemStatusBean status) throws SQLException {
		String[] params = inputStr.split(" +");
		String command = params[0].trim();
		if (isNum(command)) {
			int newLevel = Integer.parseInt(command);
			setNewLevel(word, newLevel);
			status.addLevelCount(newLevel);
			return JPWordConstants.WORD_NEXT;
		}
		if (paramCounts.get(command) == null) {
			System.out.println("命令不存在");
			return JPWordConstants.BAD_INPUT;
		}
		int paramCount = paramCounts.get(command);
		//说明中间指定了单词id
		if (paramCount < 0 && -paramCount == params.length - 1) {
			word = dao.getWordById(Integer.parseInt(params[1]));
			params[1] = params[2];
		}
		//说明中间没有指定单词id
		else if (paramCount == params.length - 1 || -paramCount == params.length) {			
		}
		else {
			System.out.println("参数数量不对");
			return JPWordConstants.BAD_INPUT;
		}
		status.setCurrentShowWord(word);
		if (command.equals("-fn")) {
			findN(word, params[1]);
		}
		else if (command.equals("a")) {
			return JPWordConstants.SHOW_ALL;
		}	
		else if (command.equals("-f")) {
			find(params[1]);
		}
		else if (command.equals("-t")) {
			trim(word);
			return JPWordConstants.SHOW_ALL;
		}
		else if (command.equals("-sj")) {
			wordSet.setJPWord(word, params[1]);
			return JPWordConstants.SHOW_ALL;
		}
		else if (command.equals("-sl")) {
			wordSet.setLevel(word, params[1]);
			return JPWordConstants.SHOW_ALL;
		}
		else if (command.equals("-sh")) {
			wordSet.setHanzi(word, params[1]);
			return JPWordConstants.SHOW_ALL;
		}
		else if (command.equals("-sc")) {
			wordSet.setCNWord(word, params[1]);
			return JPWordConstants.SHOW_ALL;
		}
		else if (command.equals("-st")) {
			wordSet.setType(word, params[1]);
			return JPWordConstants.SHOW_ALL;
		}
		else if (command.equals("ls")) {
			listWords();
		}
		else if (command.equals("s")) {
			return JPWordConstants.SKIP;
		}
		else if (command.equals("pause")) {
			status.getClock().pause();
			return JPWordConstants.CLOCK_PAUSE;
		}
		else if (command.equals("start")) {
			status.getClock().start();
			return JPWordConstants.CLOCK_START;
		}		
		else if (command.equals("count")) {
			showCounts();
		}		
		return JPWordConstants.DO_NOTHING;
	}
	private void showCounts() {
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		for (JPWord word: this.wordSet.getWords()) {
			Integer temp = counts.get(word.getLevel());
			if (temp == null) {
				counts.put(word.getLevel(), 1);
			}
			else {
				counts.put(word.getLevel(), temp.intValue() + 1);
			}
		}
		for (String key: counts.keySet()) {
			System.out.println(WordLogger.getLevelStr(key) + "\t" + counts.get(key));
		}
	}

	private void setNewLevel(JPWord word, int newLevel) throws SQLException {
		if (word.getLevel() == null) word.setLevel(newLevel + "");
		else {
			word.setLevel(word.getLevel().trim() + newLevel);
		}
		wordSet.saveWord(word);
	}

	private boolean isNum(String command) {
		try {
			Integer.parseInt(command);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	private void listWords() {
		formatListWords(this.wordSet.getWords(), userConfig.getWordFilter());
	}
	
	private void formatListWords(JPWord[] words, int[] wordFilter) {
		HashMap<Integer, Integer> maxLengths = new HashMap<Integer, Integer>();
		//取得单词各个显示部分的最大长度
		for (JPWord word: words) {
			for (int wordPart: wordFilter) {
				String value = word.getString(wordPart);
				//因为null在屏幕上显示为null，所以取4
				int length = WordLogger.getWordPartLength(value, wordPart);
				if (maxLengths.get(wordPart) == null) {
					maxLengths.put(wordPart, length);
				}
				else {
					int maxLength = maxLengths.get(wordPart);
					if (length > maxLength) maxLengths.put(wordPart, length);
				}
			}
		}
		for (JPWord word:words) {
			System.out.println(WordLogger.getWordStr(wordFilter, word, maxLengths));
		}
		System.out.println("共有" + words.length + "个单词");		
	}
	

	private void trim(JPWord word) throws SQLException {
		if (word.getCnWord() != null) word.setCnWord(word.getCnWord().trim());
		if (word.getJpWord() != null) word.setJpWord(word.getJpWord().trim());
		if (word.getHanzi() != null) word.setHanzi(word.getHanzi().trim());
		wordSet.saveWord(word);
	}

	private void findN(JPWord inWord, String index) throws SQLException {
		char hanzi = inWord.getHanzi().charAt(Integer.parseInt(index) - 1);
		find(hanzi + "");
	}
	private void find(String hanzi) throws SQLException {
		formatListWords(this.dao.findWords(hanzi), UserConfigBean.wordFilterAll);
	}

	public static void setUnitList(String command, UserConfigBean userConfig) {
		String[] unitList = null;
		if (command.contains("-")) {
			int index = command.indexOf("-");
			int startUnit = Integer.parseInt(command.substring(0, index));
			int endUnit = Integer.parseInt(command.substring(index + 1));
			unitList = new String[endUnit - startUnit + 1];
			for (int i = startUnit; i <= endUnit; i++) {
				unitList[i - startUnit] = i + "";
			}
		}
		else {
			unitList = command.split("\\s");			
		}
		userConfig.setUnitList(unitList);
	}

	public static void setLevels(String command, UserConfigBean userConfig) {
		if (command.indexOf("nr") >= 0){
			command = command.substring(0, command.indexOf("nr"));
			userConfig.setRandom(false);
		}
		String[] levelList = null;
		if (command.indexOf("a") < 0) {
			levelList = command.split("\\s");
		}
		userConfig.setLevelList(levelList);
	}

	public void saveRecord(RemStatusBean status) throws SQLException {
		dao.saveStatus(status);
	}
}
