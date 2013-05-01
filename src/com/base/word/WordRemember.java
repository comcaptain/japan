package com.base.word;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.base.word.bean.JPWord;
import com.base.word.bean.RemStatusBean;
import com.base.word.bean.UserConfigBean;
import com.base.word.bean.WordSet;
import com.base.word.util.CommandExecutor;
import com.base.word.util.JPWordConstants;
import com.base.word.util.RemClock;
import com.base.word.util.WordLogger;

/*
 * 操作指南
 * 选功能
 * 选单元
 * 选模式（只看哪种）
 * 一次显示一个，输入level或者输入指令看单词的哪个部分
 */
public class WordRemember {
	private static Scanner scanner;
	private static RemStatusBean status;
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		//根据文件获取单词列表
		//initWordList(FILE_NAME);
		status = new RemStatusBean();
		scanner = new Scanner(System.in);
		WordSet wordSet = new WordSet();
		UserConfigBean userConfig = getUserConfig();
		status.setUserConfig(userConfig);
		wordSet.filterWords(userConfig);
		status.setWordSet(wordSet);
		System.out.println("共有个" + wordSet.getWords().length + "单词");
		wordMemMain(wordSet, userConfig);
		scanner.close();
	}
	private static UserConfigBean getUserConfig() {
		UserConfigBean userConfig = new UserConfigBean();
		System.out.println("选择课时，以空格分隔：");
		CommandExecutor.setUnitList(scanner.nextLine().trim(), userConfig);
		System.out.println("选择level，以空格分隔，后面加nr表示不随机，输入a表示所有level");
		CommandExecutor.setLevels(scanner.nextLine().trim(), userConfig);	
		System.out.println("选择显示单词的哪些部分，用空格分开,t表示类型, j表示平假名,c表示中文，h表示汉字");
		userConfig.setWordFilter(scanner.nextLine().trim());
		return userConfig;
	}
	private static void wordMemMain(WordSet wordSet, UserConfigBean userConfig) throws FileNotFoundException, IOException, SQLException {
		CommandExecutor executor = new CommandExecutor(wordSet, userConfig);
		JPWord[] words = wordSet.getWords();
		RemClock clock = new RemClock();
		status.setClock(clock);
		boolean showNext = true;
		clock.start();
		for (int i = 0;i < words.length;) {
			if (showNext)
				System.out.println(WordLogger.getWordStr(userConfig.getWordFilter(), words[i])); 
			showNext = false;
			String command = scanner.nextLine().trim();
			if (command.equals("exit")) {
				System.out.print("你确定不保存这次背诵的记录直接退出？y/n ");
				if (scanner.nextLine().trim().equals("y")) {
					break;
				}
			}
			if (command.equals("save")) {
				executor.saveRecord(status);
				System.out.println("背诵记录保存成功~");
				break;
			}
			int result = executor.execute(command, words[i], status);
			switch(result) {
			case JPWordConstants.WORD_NEXT: case JPWordConstants.SKIP:
				i++; showNext = true;
			case JPWordConstants.CLOCK_PAUSE: case JPWordConstants.CLOCK_START:
				showStatus(clock, words.length, i);
				break;
			case JPWordConstants.SHOW_ALL:
				System.out.println(WordLogger.getWordStr("a", words[i]));
				break;
			case JPWordConstants.DO_NOTHING:
				break;
			}
		}
	}
	private static void showStatus(RemClock clock, int total, int passed) {
		clock.predict(total, passed);
		System.out.println(JPWordConstants.LEVEL_SHOW[0] + status.getLevelCount(0) + "\t" + 
				clock.getSpeed());
		System.out.println(JPWordConstants.LEVEL_SHOW[1] + status.getLevelCount(1) + "\t" + 
				clock.toString() + " " + passed);
		System.out.println(JPWordConstants.LEVEL_SHOW[2] + status.getLevelCount(2) + "\t" + 
				clock.getRest() + " " + (total - passed));
	}
}
