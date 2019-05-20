package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

import com.google.gson.Gson;

import exception.NotFoundedInfoException;
import item.Message_VO;
import item.User_VO;

public class Message_DAO extends ConnectionDB{
//사용자들을 입력받고, 
public String addMsg(String...strings) {
	// TODO Auto-generated method stub
	//strings[0]=user_id strings[1]=title strings[2]=msg
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	String temp=null;
	try{
		conn=getConnection();
		pstmt = conn.prepareStatement("insert into message values(?,?,?,?)");
		pstmt.setString(1, strings[0]);
		pstmt.setString(2, strings[1]);
		pstmt.setString(3, strings[2]);
		pstmt.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
		pstmt.executeUpdate();
		temp="success";
	}catch(Exception e) {
		e.printStackTrace();
	}
	return temp;

}
//user_id로 로그인 될때 자동호출 됨 
public String getMsg(String...strings) {
	// TODO Auto-generated method stub
	//strings[0]=user_id
	//M
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs = null;
	String temp=null;
	try{
		conn=getConnection();
		pstmt = conn.prepareStatement("select * from message where user_id=? order by page_reg_date desc");
		pstmt.setString(1, strings[0]);
		rs=pstmt.executeQuery();
		rs.last(); int count=rs.getRow(); rs.beforeFirst();//행 개수 구하기
		Message_VO message_VO;
		Message_VO[] list=new Message_VO[count];
		Gson gson=new Gson();
		int i=0;
		while (rs.next()) {
			message_VO=new Message_VO();
			message_VO.setUser_id(rs.getString("user_id"));
			message_VO.setTitle(rs.getString("title"));
			message_VO.setMsg(rs.getString("msg"));
			list[i++]=message_VO;
		}
		String json=gson.toJson(list);
		temp=json;
	}catch(Exception e) {
		e.printStackTrace();
	}
	return temp;

}
public String pushMsg(List<String> list,String title,String msg) throws Exception{
	// 등록된 토큰들한테 메세지를 보내줌 
    final String apiKey = "AIzaSyBhdIgK0IZX1RJ85EfGTLTJJWYvwhqFFE8";
    URL url = new URL("https://fcm.googleapis.com/fcm/send");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setRequestProperty("Authorization", "key=" + apiKey);
    conn.setDoOutput(true);
    
    
    // 이렇게 보내면 주제를 ALL로 지정해놓은 모든 사람들한테 알림을 날려준다.
//    String input = "{\"notification\" : {\"title\" : \"여기다 제목 넣기 \", \"body\" : \"여기다 내용 넣기\"}, \"to\":\"/topics/ALL\"}";
    
    // 이걸로 보내면 특정 토큰을 가지고있는 어플에만 알림을 날려준다  위에 둘중에 한개 골라서 날려주자
    String input;
    OutputStream os;
    for(String user_id:list) {
    os = conn.getOutputStream();
    input = "{\"notification\" : {\"title\" : \""+ title+" \", \"body\" : \""+ msg+"\"}, \"to\":\" "+user_id+"  \"}";
    // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
    os.write(input.getBytes("UTF-8"));
    os.flush();
    os.close();

    int responseCode = conn.getResponseCode();
    System.out.println("\nSending 'POST' request to URL : " + url);
    System.out.println("Post parameters : " + input);
    System.out.println("Response Code : " + responseCode);
    
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
    }
    in.close();
    // print result
    System.out.println(response.toString());
    
   }
return "success";

}
}
