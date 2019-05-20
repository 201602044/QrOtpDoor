package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import exception.NotFoundedInfoException;
import item.User_VO;

public class User_Dao extends ConnectionDB{
	public String login(String...strings) {
		// TODO Auto-generated method stub
		//select * from user where user_id=? and user_passwd=?
		String temp=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			Gson gson=new Gson();
			User_VO user_vo=gson.fromJson(strings[0], User_VO.class);
			conn=getConnection();
			pstmt = conn.prepareStatement("select * from user where (user_id=? and user_passwd=?)");
			pstmt.setString(1, user_vo.getUser_id());
			pstmt.setString(2, user_vo.getUser_passwd());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				temp="success";
			}
			else throw new NotFoundedInfoException();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	public String addUser(String...strings) {
		// TODO Auto-generated method stub
		//insert value into user(user_id,user_passwd,user_name,user_phone_number);
				String temp=null;
				Connection conn=null;
				PreparedStatement pstmt=null;
				ResultSet rs = null;
				try{
					Gson gson=new Gson();
					User_VO user_vo=gson.fromJson(strings[0], User_VO.class);
					conn=getConnection();
					pstmt = conn.prepareStatement("insert into user values(?,?,?,?)");
					pstmt.setString(1, user_vo.getUser_id());
					pstmt.setString(2, user_vo.getUser_passwd());
					pstmt.setString(3, user_vo.getUser_name());
					pstmt.setString(4, user_vo.getUser_phone_number());
					pstmt.executeUpdate();			
						temp="success";
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				return temp;
	}
	public String changePasswd(String...strings) {
		// TODO Auto-generated method stub
		//update user set user_passwd=? where (user_id=? and user_passwd=?)
		//json 요소로 파싱하는게 좋음 
		String temp=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(strings[0]);
			conn=getConnection();
			pstmt = conn.prepareStatement("update user set user_passwd=? where (user_id=? and user_passwd=?)");
			pstmt.setString(1, element.getAsJsonObject().get("user_re_passwd").getAsString());
			pstmt.setString(2, element.getAsJsonObject().get("user_id").getAsString());
			pstmt.setString(3, element.getAsJsonObject().get("user_passwd").getAsString());
			pstmt.executeUpdate();			
				temp="success";
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	public String deleteUser(String ...strings ) {
		// TODO Auto-generated method stub
		//delete from user where user_id=?
		String temp=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			Gson gson=new Gson();
			User_VO user_vo=gson.fromJson(strings[0], User_VO.class);
			conn=getConnection();
			pstmt = conn.prepareStatement("delete from user where user_id=?");
			pstmt.setString(1, user_vo.getUser_id());
			pstmt.executeUpdate();			
				temp="success";
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	public String changeUserInfo(String...strings) {
		// TODO Auto-generated method stub
		//update user set user_name=?,user_phone_number=? where user_id=?
		String temp=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			Gson gson=new Gson();
			User_VO user_vo=gson.fromJson(strings[0], User_VO.class);
			conn=getConnection();
			pstmt = conn.prepareStatement("update user set user_name=?,user_phone_number=? where user_id=?");
			pstmt.setString(1, user_vo.getUser_name());
			pstmt.setString(2, user_vo.getUser_phone_number());
			pstmt.setString(3, user_vo.getUser_id());
			pstmt.executeUpdate();			
				temp="success";
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
}
