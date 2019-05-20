package component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import exception.AlreadyChangeKeyException;
import exception.NotFoundedInfoException;
import item.DoorUserRelation_VO;

import java.lang.Thread;
public class OTPThread{
	DoorUserRelation_VO doorUserRelation_VO;
	BlcokAlgorithm_Service cbc;
	Connection conn;
	int time;
	public OTPThread(DoorUserRelation_VO doorUserRelation_VO,BlcokAlgorithm_Service cbc,Connection conn,int time) {
		// TODO Auto-generated constructor stub
		//시간은 otp 설정 초단위로 받아온다
		this.doorUserRelation_VO=doorUserRelation_VO;
		this.cbc=cbc;
		this.conn=conn;
		this.time=time*1000;
	}
	
	public void removeKey() throws Exception{
		//OTP를 위해 1분뒤 key를 삭제해주는 쓰레드 
		Runnable r = new Runnable() {
			public void run() {
				try {
					Thread.sleep(time);
					PreparedStatement pstmt=null;
					ResultSet rs = null;
					pstmt = conn.prepareStatement("select door_key from dooruserrelation where (door_id=? and user_id=?)");
					pstmt.setString(1, doorUserRelation_VO.getDoor_id());
					pstmt.setString(2, doorUserRelation_VO.getUser_id());
					rs=pstmt.executeQuery();
					if(rs.next()) {
						String key=rs.getString("door_key");
						if(key.equals(cbc.decrypt(doorUserRelation_VO.getDoor_key().getBytes()))) {
							pstmt = conn.prepareStatement("update dooruserrelation set door_key=? where (door_id=? and user_id=?)");
							pstmt.setString(1, null);
							pstmt.setString(2, doorUserRelation_VO.getDoor_id());
							pstmt.setString(3, doorUserRelation_VO.getUser_id());
							pstmt.executeUpdate();
							//키가 그대로 있으면 삭제한다.
						}
						else throw new AlreadyChangeKeyException();
					}
					else throw new NotFoundedInfoException();

				}
			 catch (Exception e) {
				 e.printStackTrace();
			}
		}
	};
	Thread thread = new Thread(r);
	// 여기까지가 쓰레드의 정의부분

	thread.start();
	// 이게 쓰레드의 실행 명령
}

}