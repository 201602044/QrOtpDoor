package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import component.BlcokAlgorithm_Service;
import component.OTPThread;
import exception.NotFoundedInfoException;
import item.DoorUserRelation_VO;

public class DoorUserRelation_DAO extends ConnectionDB {
	BlcokAlgorithm_Service cbc;
	public DoorUserRelation_DAO() {
		// TODO Auto-generated constructor stub
		cbc=new BlcokAlgorithm_Service();
	}
	
	public String requestKey(String...strings) {
		// TODO Auto-generated method stub
		// update dooruserrelation set  door_key=? where ( door_id=? and user_id=?)
		//60초뒤에 삭제 해야함 or 확인할때 삭제 해야함 그리고 기존의 키가 바뀌어있으면 건들면 안 돼
		String temp=null;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		try{
			Gson gson=new Gson();
			DoorUserRelation_VO doorUserRelation_VO =gson.fromJson(strings[0], DoorUserRelation_VO.class);
			conn=getConnection();
			//DB연결후 받은 데이터로 VO생성
			String key=doorUserRelation_VO.getDoor_id()
					+doorUserRelation_VO.getUser_id()
					+System.currentTimeMillis();
			//Key생성
			pstmt = conn.prepareStatement("update dooruserrelation set  door_key=? where ( door_id=? and user_id=?)");
			pstmt.setString(1, key);
			pstmt.setString(2, doorUserRelation_VO.getDoor_id());
			pstmt.setString(3, doorUserRelation_VO.getUser_id());
			pstmt.executeUpdate();			
			doorUserRelation_VO.setDoor_key(new String(cbc.encrypt(key)));
			temp=gson.toJson(doorUserRelation_VO);
			//db 키추가후 반환
			//이래도 돼?
			//만약 잘못된 값이 들어가면 gson에서 파싱이 안되니 걱정ㄴㄴ
			OTPThread otp=new OTPThread(doorUserRelation_VO,cbc,conn,60);
			//하단에 쓰레드 정의
			otp.removeKey();

	}catch(Exception e) {
		e.printStackTrace();
	}
	return temp;
}
	
public String compareKey(String...strings) {
	// 도어락->웹서버에서 인식할때 하는것, 틀리면 안열리고 맞으면 열림 
	//3회이상일시 오류메세지
	//열릴시 키 삭제 
	//입력받을때 key와 door을 받는다.
	String temp=null;
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	try{
		Gson gson=new Gson();
		DoorUserRelation_VO doorUserRelation_VO =gson.fromJson(strings[0], DoorUserRelation_VO.class);
		conn=getConnection();
		String receiveKey=cbc.decrypt(doorUserRelation_VO.getDoor_key().getBytes());
		pstmt = conn.prepareStatement("select user_id from dooruserrelation where (door_id=? and door_key=?)");
		pstmt.setString(1, doorUserRelation_VO.getDoor_id());
		pstmt.setString(2, receiveKey);
		rs=pstmt.executeQuery();
		Message_DAO messageDAO=new Message_DAO();
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
		Date date=new Date();
		//복호화된 받은키
		if(rs.next()) {
				//인식해서 받아온 키가 서버에 저장되어있는 키라면?
				pstmt = conn.prepareStatement("update dooruserrelation set door_key=? where (door_id=? and door_key=?)");
				pstmt.setString(1, null);
				pstmt.setString(2, doorUserRelation_VO.getDoor_id());
				pstmt.setString(3, receiveKey);
				pstmt.executeUpdate();
				//키가 그대로 있으면 삭제한다.
				pstmt = conn.prepareStatement("update door set door_tryNum=? where door_id=?");
				pstmt.setInt(1, 0);
				pstmt.setString(2, doorUserRelation_VO.getDoor_id());
				pstmt.executeUpdate();
				//시도횟수 초기화
				
				messageDAO.addMsg(doorUserRelation_VO.getDoor_id(),rs.getString("user_id")+"님",format.format(date)+"에 출입 하였습니다.");
				//들어온 시간을 보내줘야함 
				temp="success";
				return temp;
			//맞으면 키삭제, 성공반환
			
		}
		else {
			//도어락에 잘못된 키로 인식을 시도했으면?
			pstmt = conn.prepareStatement("select door_tryNum from door where door_id=?");
			pstmt.setString(1, doorUserRelation_VO.getDoor_id());
			rs=pstmt.executeQuery();
			if(rs.next()) {
				//등록된 도어락이 있을경우
				int tryNum=(rs.getInt("door_tryNum")+1);
				//잘못된 QR코드를 인식했을때 경고 횟수를 추가한다. 3회이상이면 경고 누적 				
				pstmt = conn.prepareStatement("update door set door_tryNum=door_tryNum+1 where door_id=? ");
				pstmt.setString(1, doorUserRelation_VO.getDoor_id());
				pstmt.executeUpdate();
				//시도횟수 증가 
				if(tryNum>=3) {
					messageDAO.pushMsg(getDoorUserId(doorUserRelation_VO.getDoor_id()),"Warning",format.format(date)+"에 "+tryNum+"회이상 잘못된 접근이 발생했습니다");
					//경고메세지를 보내줘야함 
					return temp;
				}
			}
			else throw new NotFoundedInfoException();
			//등록된 도어락이 없을 경ㅇ ㅜ
				
		}
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	return temp;
}

public String addDoorUser(String...strings) {
	// TODO Auto-generated method stub
	//"insert into dooruserrelation values(?,?,?,?)"
	String temp=null;
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	try{
		Gson gson=new Gson();
		DoorUserRelation_VO doorUserRelation_VO =gson.fromJson(strings[0], DoorUserRelation_VO.class);
		conn=getConnection();
		pstmt = conn.prepareStatement("insert into dooruserrelation values(?,?,?,?)");
		pstmt.setString(1, doorUserRelation_VO.getDoor_id());
		pstmt.setString(2, doorUserRelation_VO.getUser_id());
		pstmt.setString(3, null);
		pstmt.setString(4, doorUserRelation_VO.getDoor_name());
		pstmt.executeUpdate();			
		temp="success";

	}catch(Exception e) {
		e.printStackTrace();
	}
	return temp;
}
public String deleteDoorUser(String...strings) {
	// TODO Auto-generated method stub
	//delete from dooruserrelation where (door_id=? and user_id=?);
	String temp=null;
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	try{
		Gson gson=new Gson();
		DoorUserRelation_VO doorUserRelation_VO =gson.fromJson(strings[0], DoorUserRelation_VO.class);
		conn=getConnection();
		pstmt = conn.prepareStatement("delete from dooruserrelation where (door_id=? and user_id=?)");
		pstmt.setString(1, doorUserRelation_VO.getDoor_id());
		pstmt.executeUpdate();			
		temp="success";

	}catch(Exception e) {
		e.printStackTrace();
	}
	return temp;
}
public List<String> getDoorUserId(String...strings) {

	// TODO Auto-generated method stub
	//"select user_id from dooruserrelation where door_id =? "
	//도어락 id에 등록된 사용자들을 불러온다.
	// List<String>형태로 반환을 해주는 형태ㅗ 만들어야한다 .
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	List list=new LinkedList<String>();
	try{
		conn=getConnection();
		pstmt = conn.prepareStatement("select user_id from dooruserrelation where door_id =? ");
		pstmt.setString(1, strings[0]);
		rs=pstmt.executeQuery();

		while (rs.next()) {
			list.add(rs.getString("user_id"));
		}

	}catch(Exception e) {
		e.printStackTrace();
	}
	return list;
}
public String getDoorUserInfo(String...strings) {

	// TODO Auto-generated method stub
	//"select user_id from dooruserrelation where door_id =? "
	//유저에 등록된 도어락들을 불러온다.
	//warning클래스에서 받아서 List<String>형태로 반환을 해주는 형태ㅗ 만들어야한다 .
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	String temp=null;
	try{
		conn=getConnection();
		pstmt = conn.prepareStatement("select * from dooruserrelation where user_id =? ");
		pstmt.setString(1, strings[0]);
		rs=pstmt.executeQuery();
		rs.last(); int count=rs.getRow(); rs.beforeFirst();//행 개수 구하기
		DoorUserRelation_VO doorUserRelation_VO;
		DoorUserRelation_VO[] list=new DoorUserRelation_VO[count];
		Gson gson=new Gson();
		int i=0;
		while (rs.next()) {
			
			doorUserRelation_VO=new DoorUserRelation_VO();
			doorUserRelation_VO.setDoor_id(rs.getString("door_id"));
			doorUserRelation_VO.setDoor_key(rs.getString("door_key"));
			doorUserRelation_VO.setDoor_name(rs.getString("door_name"));
			doorUserRelation_VO.setUser_id(rs.getString("user_id"));
			list[i++]=doorUserRelation_VO;
		}
		String json=gson.toJson(list);
		temp=json;
	}catch(Exception e) {
		e.printStackTrace();
	}
	return temp;
}
}