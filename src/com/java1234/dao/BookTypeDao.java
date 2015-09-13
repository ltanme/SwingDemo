package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java1234.model.BookType;
import com.java1234.util.StringUtil;

public class BookTypeDao {
	
	public int add(Connection con,BookType booktype) throws Exception{
		String sql = "insert into t_bookType value(null,?,?)";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, booktype.getBookTypeName());
		psmt.setString(2, booktype.getBookTypeDesc());
		return psmt.executeUpdate();
	}
	
	public ResultSet list(Connection con,BookType bookType) throws Exception{
		StringBuffer sb = new StringBuffer("select * from t_bookType");  
		if(StringUtil.isNotEmpty(bookType.getBookTypeName())){
			sb.append(new String(" and bookTypeName like '%"+bookType.getBookTypeName()+"%'"));
		} 
		PreparedStatement pstmt = con.prepareStatement(new String(sb.toString().replaceFirst("and", "where"))); 
		return pstmt.executeQuery();
	}
	
	public int delete(Connection con,String id)throws Exception{
		String sql ="delete from t_bookType where id=?"; 
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, id);
		return psmt.executeUpdate();
	}

	public int update(Connection con,BookType bookType)throws Exception{
		String sql ="update t_bookType set bookTypeName=?,bookTypeDesc=? where id=?"; 
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1,bookType.getBookTypeName());
		psmt.setString(2,bookType.getBookTypeDesc());
		psmt.setInt(3,bookType.getId());
		return psmt.executeUpdate();
	}
}
