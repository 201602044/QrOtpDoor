package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import item.User_VO;
import model.User_Dao;

class UserDao_Test {
	User_VO user_VO;
	User_Dao user_Dao;
	@Test
	void testLogin() throws Exception{
		 user_VO=new User_VO();
		 user_VO.setUser_id("a");
		 user_VO.setUser_passwd("a");
		 Gson gson=new Gson();
		 String json=gson.toJson(user_VO);
		 user_Dao=new User_Dao();
		 String result=user_Dao.login(json);
		 assertEquals("success", result);
		 System.out.println(result);
	}
	@Test
	void testAddUser() throws Exception{
		 user_VO=new User_VO();
		 user_VO.setUser_id("c");
		 user_VO.setUser_passwd("a");
		 user_VO.setUser_name("a");
		 user_VO.setUser_phone_number("a");
		 Gson gson=new Gson();
		 String json=gson.toJson(user_VO);
		 user_Dao=new User_Dao();
		 String result=user_Dao.addUser(json);
		 assertEquals("success", result);
		 System.out.println(result);
	}
	
	@Test
	void testchangeUserPasswd() throws Exception{
		//property로 보내서 테스트 
		Gson gson=new Gson();
		 JsonObject jsonObject=new JsonObject();
		 jsonObject.addProperty("user_re_passwd", "b");
		 jsonObject.addProperty("user_id", "a");
		 jsonObject.addProperty("user_passwd", "a");
		 String json=gson.toJson(jsonObject);
		 user_Dao=new User_Dao();
		 String result=user_Dao.changePasswd(json);
		 assertEquals("success", result);
		 System.out.println(result);
	}
	@Test
	void testDeleteUser() throws Exception{
		 user_VO=new User_VO();
		 user_VO.setUser_id("c");
		 Gson gson=new Gson();
		 String json=gson.toJson(user_VO);
		 User_Dao user_Dao=new User_Dao();
		 String result=user_Dao.deleteUser(json);
		 assertEquals("success", result);
		 System.out.println(result);
	}
	@Test
	void testChangeUserInfo() throws Exception{
		 user_VO=new User_VO();
		 user_VO.setUser_id("b");
		 user_VO.setUser_passwd("a");
		 user_VO.setUser_name("c");
		 user_VO.setUser_phone_number("c");
		 Gson gson=new Gson();
		 String json=gson.toJson(user_VO);
		 User_Dao user_Dao=new User_Dao();
		 String result=user_Dao.changeUserInfo(json);
		 assertEquals("success", result);
		 System.out.println(result);
	}
}
