package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.google.gson.Gson;

import item.SharedUser_VO;

public class SharedUser_DAO extends ConnectionDB{
	public String addSharedUser(String...strings) {
		// TODO Auto-generated method stub
		//insert into shareduser values (?,?,?,?)
		String temp=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			Gson gson=new Gson();
			SharedUser_VO sharedUser_VO=gson.fromJson(strings[0], SharedUser_VO .class);
			conn=getConnection();
			pstmt = conn.prepareStatement("insert into shareduser values (?,?,?,?)");
			pstmt.setString(1, sharedUser_VO.getUser_id());
			pstmt.setString(2,sharedUser_VO.getShareduser_name());
			pstmt.setString(3,sharedUser_VO.getShareduser_phone_number());
			//new Timestamp(System.currentTimeMillis())
			pstmt.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
			//Timestamp 중 import java.sql.Timestamp 해야 오류 ㄴ 
			pstmt.executeUpdate();
			temp="success";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

}
