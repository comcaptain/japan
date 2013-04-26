package com.base.word.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.base.word.bean.JPWord;
import com.base.word.bean.RemStatusBean;
import com.base.word.util.DBUtil;

public class JPWordDao {
	private Connection conn;
	public JPWordDao() {
		conn = DBUtil.getConnection();
	}
	public void insertJPWord(JPWord word) throws SQLException {
		String sql = "insert into jpword values(?,?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, (int)word.getWordId());
		stmt.setString(2, word.getJpWord());
		stmt.setString(3, word.getCnWord());
		stmt.setString(4, word.getHanzi());
		stmt.setString(5, word.getType());
		stmt.setString(6, word.getUnit() + "");
		stmt.setString(7, word.getLevel() + "");
		stmt.executeUpdate();
		System.out.println(word.getWordId() + ":" + word.getCnWord());
		stmt.close();
	}
	public JPWord getWordById(int wordId) throws SQLException {
		String sql = "select * from jpword where wordid = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, wordId);		
		JPWord result = getSingleWord(stmt.executeQuery());
		stmt.close();
		return result;
	}
	private JPWord getSingleWord(ResultSet rs) throws SQLException {
		if (!rs.next()) return null;
		JPWord result = new JPWord();
		result.setWordId(rs.getInt("wordid"));
		result.setCnWord(rs.getString("cnword"));
		result.setHanzi(rs.getString("hanzi"));
		result.setJpWord(rs.getString("jpword"));
		result.setLevel(rs.getString("level"));
		result.setType(rs.getString("type"));
		result.setUnit(rs.getString("unit"));
		return result;
	}
	public JPWord[] getWordList(String[] unitList, String[] levelList) throws SQLException {
		LinkedList<JPWord> words = new LinkedList<JPWord>();
		String sql = "select * from jpword where 2 > 1 ";
		boolean unitFilter = unitList != null;
		boolean levelFilter = levelList != null;
		if (unitFilter) sql += " and unit in " + DBUtil.getDerbyArray(unitList);
		if (levelFilter) sql += " and level in " + DBUtil.getDerbyArray(levelList);
		Statement stmt = conn.createStatement();
		JPWord word;
		ResultSet rs = stmt.executeQuery(sql);
		while ((word = getSingleWord(rs)) != null) {
			words.add(word);
		}
		stmt.close();
		return words.toArray(new JPWord[0]);
	}
	public void saveWord(JPWord word) throws SQLException {
		String sql = "update jpword set level=?, jpword=?, cnword=?, hanzi=? where wordid=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, word.getLevel());
		stmt.setString(2, word.getJpWord());
		stmt.setString(3, word.getCnWord());
		stmt.setString(4, word.getHanzi());
		stmt.setInt(5, word.getWordId());
		stmt.executeUpdate();
		stmt.close();		
	}
	public void saveStatus(RemStatusBean status) {
		for (int i = 0; i < status.getLevelCountLength(); i++) {
			if (status.getLevelCount(0) == 0) continue;
		}
		String sql = "insert into ";
	}
}
