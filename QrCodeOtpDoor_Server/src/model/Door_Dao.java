package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.Gson;

import exception.NotFoundedInfoException;
import item.Door_VO;

public class Door_Dao  extends ConnectionDB{
//문을 제어하는 클래스 
	public String addDoor(String...strings) {
		// TODO Auto-generated method stub
				//insert into door values (?,?(위도),?(경도))
				String temp=null;
				Connection conn=null;
				PreparedStatement pstmt=null;
				ResultSet rs = null;
				try{
					Gson gson=new Gson();
					Door_VO door_vo=gson.fromJson(strings[0], Door_VO.class);
					conn=getConnection();
					pstmt = conn.prepareStatement("insert into door values (?,?,?)");
					pstmt.setString(1, door_vo.getDoor_id());
					pstmt.setString(2, door_vo.getDoor_latitude());
					//위도
					pstmt.setString(3, door_vo.getDoor_longitude());
					pstmt.executeUpdate();
						temp="success";
				}catch(Exception e) {
					e.printStackTrace();
				}
				return temp;
	}
	public String deleteDoor(String...strings) {
		// TODO Auto-generated method stub
		//insert into door values (?,?)
		String temp=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			conn=getConnection();
			pstmt = conn.prepareStatement("delete from door where door_id=?");
			pstmt.setString(1, strings[0]);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	public String getDoorLocation(String...strings) {
		// TODO Auto-generated method stub
				//select * from door where door_id=?
				String temp=null;
				Connection conn=null;
				PreparedStatement pstmt=null;
				ResultSet rs = null;
				try{
					Gson gson=new Gson();
					Door_VO door_vo=gson.fromJson(strings[0], Door_VO.class);
					conn=getConnection();
					pstmt = conn.prepareStatement("select * from door where door_id=?");
					pstmt.setString(1, door_vo.getDoor_id());
					rs=pstmt.executeQuery();
					if(rs.next()) {
						door_vo.setDoor_latitude(rs.getString("door_langtitude"));
						door_vo.setDoor_longitude(rs.getString("door_longitude"));
						temp=gson.toJson(door_vo);
					}
					else throw new NotFoundedInfoException();
						
				}catch(Exception e) {
					e.printStackTrace();
				}
				return temp;
	}
}
