package com.base.word.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

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
		JPWord result = getJPWord(stmt.executeQuery());
		stmt.close();
		return result;
	}
	private JPWord getJPWord(ResultSet rs) throws SQLException {
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
		if (levelFilter) {
			if (levelList.length == 1 && levelList[0].equals("d")) {
				sql += " and level is NULL";
			}
			else {
				sql += " and (" + DBUtil.getDerbyLikeArray("level", levelList) + ")";
			}
		}
		Statement stmt = conn.createStatement();
		JPWord word;
		ResultSet rs = stmt.executeQuery(sql);
		while ((word = getJPWord(rs)) != null) {
			words.add(word);
		}
		stmt.close();
		return words.toArray(new JPWord[0]);
	}
	public void saveWord(JPWord word) throws SQLException {
		String sql = "update jpword set level=?, jpword=?, cnword=?, hanzi=?, type=? where wordid=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, word.getLevel());
		stmt.setString(2, word.getJpWord());
		stmt.setString(3, word.getCnWord());
		stmt.setString(4, word.getHanzi());
		stmt.setString(5, word.getType());
		stmt.setInt(6, word.getWordId());
		stmt.executeUpdate();
		stmt.close();		
	}
	public void saveStatus(RemStatusBean status) throws SQLException {
		try {
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			String sql = "select max(record_id) as maxid from mem_record";
			ResultSet rs = stmt.executeQuery(sql);
			int recordId = 1;
			if (rs.next() && rs.getObject("maxid") != null) {
				int maxid = rs.getInt("maxid");
				recordId = maxid + 1;
			}
			sql = "insert into mem_record(record_id, timelength, wordnum) values(" + recordId +
					" ," + status.getClock().getEclapsed() + 
							", " + status.getWordCount() + ")";
			stmt.executeUpdate(sql);
			for (int i = 0; i < status.getLevelCountLength(); i++) {
				if (status.getLevelCount(i) == 0) continue;
				sql = "insert into level_record(record_id, level, wordnum) values(" + recordId + 
						"," + i +
						" ," + status.getLevelCount(i) + 
						" )";
				stmt.executeUpdate(sql);
			}
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}
		finally {
			conn.setAutoCommit(true);
		}
	}
	public JPWord[] findWords(String hanzi) throws SQLException {
		List<JPWord> wordList = new LinkedList<JPWord>();
		Statement stmt = this.conn.createStatement();
		String sql = "select * from jpword where hanzi like '%" + hanzi + 
				"%'";
		ResultSet rs = stmt.executeQuery(sql);
		JPWord word = null;
		while ((word = this.getJPWord(rs)) != null) {
			wordList.add(word);
		}
		return wordList.toArray(new JPWord[0]);
	}
}
