package com.base.word.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.base.word.util.DBUtil;
import com.base.word.util.WordLogger;

public class SqlExecutor {
	private Connection conn;
	public SqlExecutor() {
		conn = DBUtil.getConnection();
	}
	public SqlExecutor(Connection conn) {
		this.conn = conn;
	}
	public String execute(String sql) {
		try {
			Statement stmt = conn.createStatement();
			boolean isQuery = stmt.execute(sql);
			if (isQuery) {
				return showResultSetTable(stmt.getResultSet());
			}
			else {
				int count = stmt.getUpdateCount();
				return count + (count <= 1 ? " row is" : " rows are") + " affected.";
			}
		} catch (SQLException e) {
//			StringWriter strWriter = new StringWriter();
//			e.printStackTrace(new PrintWriter(strWriter));
//			return strWriter.toString();
			return e.toString();
		}
	}
	//还是按照非英数字是英数字的两倍宽度来做
	private String showResultSetTable(ResultSet rs) throws SQLException {
		String[] headers = new String[rs.getMetaData().getColumnCount()];
		ResultSetMetaData metaData = rs.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			headers[i - 1] = metaData.getColumnName(i);
		}
		List<String[]> tableData = new LinkedList<String[]>();
		tableData.add(headers);
		while (rs.next()) {
			String[] data = new String[metaData.getColumnCount()];
			for (int i = 1; i <= data.length; i++) {
				data[i - 1] = rs.getString(i);
			}
			tableData.add(data);
		}
		return getCommandTable(tableData.toArray(new String[0][0]));
	}
	private String getCommandTable(String[][] tableData) {
		StringBuffer result = new StringBuffer();
		int[] maxLengthCounts = analyzeMaxLengths(tableData);
		int gap = 2;
		boolean isFirst = true;
		for (String[] row: tableData) {
			for (int i = 0; i < row.length; i++) {
				String cell = row[i];
				int padCount = WordLogger.getWordPartLength(cell);
				padCount = maxLengthCounts[i] - padCount + gap;
				result.append(WordLogger.rightAdd(cell, padCount, ' '));
			}
			result.append("\n");
			if (isFirst) {
				int width = 0;
				for (int length: maxLengthCounts) {
					width += length;
				}
				width += row.length * gap;
				result.append(WordLogger.rightAdd("", width, '-'));
				result.append("\n");
				isFirst = false;
			}
		}
		return result.toString();
	}
	private int[] analyzeMaxLengths(String[][] tableData) {
		int[] maxLengthCounts = new int[tableData[0].length];
		for (String[] row: tableData) {
			for (int i = 0; i < row.length; i++) {
				int length = WordLogger.getWordPartLength(row[i]);
				if (length > maxLengthCounts[i]) maxLengthCounts[i] = length;
			}
		}
		return maxLengthCounts;
	}
}
