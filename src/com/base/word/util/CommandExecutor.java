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
		this.paramCounts.put("-sj", 1);
		this.paramCounts.put("-sh", 1);
		this.paramCounts.put("-sc", 1);
		this.paramCounts.put("-sl", 1);
		this.paramCounts.put("ls", 0);
		this.paramCounts.put("s", 0);
		this.paramCounts.put("exit", 0);
		this.paramCounts.put("num", 0);
		this.paramCounts.put("pause", 0);
		this.paramCounts.put("start", 0);
		this.paramCounts.put("a", 0);
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
		if (!(paramCount == params.length - 1 || paramCount == params.length - 2)) {
			System.out.println("参数数量不对");
			return JPWordConstants.BAD_INPUT;
		}
		if (command.equals("-fn")) {
			findN(word, params[1]);
		}
		else if (command.equals("a")) {
			return JPWordConstants.SHOW_ALL;
		}	
		else if (command.equals("-f")) {
			find(word, params[1]);
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
		return JPWordConstants.DO_NOTHING;
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
		for (JPWord word: this.wordSet.getWords()) {
			System.out.println(WordLogger.getWordStr(userConfig.getWordFilter(), word));
		}
		System.out.println("共有个" + this.wordSet.getWords().length + "单词");
	}

	private void trim(JPWord word) throws SQLException {
		if (word.getCnWord() != null) word.setCnWord(word.getCnWord().trim());
		if (word.getJpWord() != null) word.setJpWord(word.getJpWord().trim());
		if (word.getHanzi() != null) word.setHanzi(word.getHanzi().trim());
		wordSet.saveWord(word);
	}

	private void findN(JPWord inWord, String index) {
		char hanzi = inWord.getHanzi().charAt(Integer.parseInt(index) - 1);
		find(inWord, hanzi + "");
	}
	private void find(JPWord inWord, String hanzi) {
		int count = 0;
		for (JPWord word: this.wordSet.getWords()) {
			if (word.getHanzi() != null && word.getHanzi().contains(hanzi + "")) {
				System.out.println(WordLogger.getWordStr(JPWordConstants.SHOW_ALL_FILTER, word));
				count++;
			}
		}
		System.out.println("一共找到个" + count + "单词");
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
