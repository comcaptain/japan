package com.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class FileSearcher {
	
	private File dir;
	private String filter = "*\\.*";
	private String encoding = "utf-8";
	private int count = 0;
	public File getDir() {
		return dir;
	}
	public void setDir(File dir) {
		this.dir = dir;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public FileSearcher() {
		
	}	
	public static void main(String[] args) {
		FileSearcher s = new FileSearcher();
		s.setDir(new File("C:/Program Files/Apache Software Foundation/Apache2.2/htdocs/calendar"));
		s.setFilter("^.*php$");
		s.search("EncryptedPassword()");
	}
	public void search(String keyword) {
		count = 0;
		for (File file: getSubFiles(dir)) {
			searchFile(file, keyword);
		}
	}
	private void searchFile(File file, String keyword) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
			String line = null;
			int lineNum = 0;
			while ((line = reader.readLine()) != null) {
				lineNum++;
				if (line.indexOf(keyword) >= 0) {
					System.out.println("第" + (++count) + "个搜索结果（line " + lineNum + "） " + file.getAbsolutePath() + 
							":\n" + line.trim());
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<File> getSubFiles(File file) {
		List<File> result = new LinkedList<File>();
		if (!file.isDirectory()) {
			if (file.getName().matches(filter))result.add(file);
			return result;
		}
		for (File subFile: file.listFiles()) {
			result.addAll(getSubFiles(subFile));
		}
		return result;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
